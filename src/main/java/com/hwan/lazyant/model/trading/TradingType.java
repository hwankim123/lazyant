package com.hwan.lazyant.model.trading;

import java.util.function.Function;

public enum TradingType {
    BUY((input) -> input),
    SELL((input) -> -input);

    private final Function<Double, Double> function;

    TradingType(Function<Double, Double> operator) {
        this.function = operator;
    }

    public Double applySign(Double input) {
        return this.function.apply(input);
    }
}
