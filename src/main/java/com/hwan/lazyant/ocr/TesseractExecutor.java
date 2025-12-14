package com.hwan.lazyant.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TesseractExecutor implements OcrExecutor {

    private final Tesseract tesseract;

    @Override
    public List<TradingWordsGroup> extractTradings(File imageFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            List<Word> words = tesseract.getWords(bufferedImage, ITessAPI.TessPageIteratorLevel.RIL_WORD);

            List<TradingWordsGroup> tradingWordsGroups = new ArrayList<>();
            StringBuilder unknownWord = new StringBuilder();
            TradingWordsGroup.TradingWordsGroupBuilder builder = TradingWordsGroup.builder();
            int x = -1; // word의 x축 좌표값
            int lastWidth = 0; // i번째 word와 i-1번째 word와의 거리
            for(Word word : words) {
                if(x > word.getBoundingBox().x) {
                    if(!unknownWord.isEmpty()) {
                        builder = builder.append(new TradingWord(TradingWord.Meaning.UNKNOWN, unknownWord.toString()));
                        unknownWord = new StringBuilder();
                    }
                }
                x = word.getBoundingBox().x;
                if(word.getText().matches("^\\$\\d+\\.\\d+$")) { // 달러 표현식. $1.43
                    if(!unknownWord.isEmpty()) { // unknownWord가 쌓여있다면 append
                        builder = builder.append(new TradingWord(TradingWord.Meaning.UNKNOWN, unknownWord.toString()));
                        unknownWord = new StringBuilder();
                    }
                    builder = builder.append(new TradingWord(TradingWord.Meaning.AMOUNT, word.getText()));
                } else if(word.getText().matches("^(\\d+)(\\.\\d+)?$")) { // 소숫점 표현식. 0.123124
                    unknownWord = new StringBuilder(word.getText());
                } else if(unknownWord.toString().matches("^(\\d+)(\\.\\d+)?$") && word.getText().equals("주")) { // 소숫점 표현식 + "주" -> unknownWord는 주문 수량이었음
                    tradingWordsGroups.add(
                            builder.append(new TradingWord(TradingWord.Meaning.QUANTITY, unknownWord.toString()))
                                    .build()
                    );
                    builder = TradingWordsGroup.builder();
                    unknownWord = new StringBuilder();
                } else if(unknownWord.toString().matches("^(\\d+)(\\.\\d+)?$") && !word.getText().equals("주")) { // 소숫점 표현식 + "주"가 아님 -> unknownWord는 날짜였음
                    builder = builder.append(new TradingWord(TradingWord.Meaning.TRADE_AT, unknownWord.toString()));
                    unknownWord = new StringBuilder(word.getText());
                } else {
                    unknownWord.append(word.getText());
                    if(unknownWord.toString().equals("구매완료")) {
                        builder = builder.append(new TradingWord(TradingWord.Meaning.TRADING_TYPE, unknownWord.toString()));
                        unknownWord = new StringBuilder();
                    } else {
                        if(lastWidth != 0 && lastWidth * 5 < word.getBoundingBox().x - x) { // 너비가 급격하게 늘어났다면 이전까지를 한 단어로 인식
                            builder = builder.append(new TradingWord(TradingWord.Meaning.UNKNOWN, unknownWord.toString()));
                            unknownWord = new StringBuilder();
                        } else {
                            lastWidth = word.getBoundingBox().x - x;
                        }
                    }
                }
            }

            return tradingWordsGroups;
        } catch (IOException e) {
            throw new RuntimeException("OCR failed", e);
        }
    }
}
