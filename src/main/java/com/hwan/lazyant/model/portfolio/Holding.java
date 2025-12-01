package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.model.stock.type.Market;

public record Holding(
        String name,
        Market market,
        String symbol,
        Long portfolioItemId,
        Factor factor,
        double principal,
        double quantity
) {

    public double evaluateEvaluatedAmount(double marketPrice) {
        return Math.round(quantity * marketPrice) * 100 / 100.0;
    }

    public double evaluateProfitLoss(double marketPrice) {
        return this.evaluateEvaluatedAmount(marketPrice) - principal;
    }

    public double evaluateProfitLossRate(double marketPrice) {
        return Math.round((this.evaluateProfitLoss(marketPrice) / principal) * 100 * 100) / 100.0;
    }
}
