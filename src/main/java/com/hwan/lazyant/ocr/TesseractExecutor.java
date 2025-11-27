package com.hwan.lazyant.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class TesseractExecutor implements OcrExecutor {

    private final Tesseract tesseract;

    @Override
    public void extractTradings(File imageFile) {
        try {
            String text = tesseract.doOCR(imageFile);
            log.info("text: {}", text);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }
}
