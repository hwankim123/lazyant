package com.hwan.lazyant.ocr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TradingWordsGroup {
    private final List<TradingWord> tradingWords;

    private TradingWordsGroup(List<TradingWord> tradingWords) {
        this.tradingWords = tradingWords;
    }

    public static TradingWordsGroupBuilder builder() {
        return new TradingWordsGroupBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TradingWordsGroupBuilder {
        private final List<TradingWord> tradingWords = new ArrayList<>();

        public TradingWordsGroupBuilder append(TradingWord tradingWord) {
            this.tradingWords.add(tradingWord);
            return this;
        }

        public TradingWordsGroup build() {
            return new TradingWordsGroup(this.tradingWords);
        }
    }
}
