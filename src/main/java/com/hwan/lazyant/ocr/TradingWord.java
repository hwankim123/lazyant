package com.hwan.lazyant.ocr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradingWord {

    private Meaning meaning;
    private String value;

    @Override
    public String toString() {
        return "TradingWord{" + meaning + ", " + value + '}';
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public enum Meaning {
        UNKNOWN,
        STOCK_NAME,
        TRADING_TYPE,
        QUANTITY,
        AMOUNT,
        TRADE_AT
    }
}
