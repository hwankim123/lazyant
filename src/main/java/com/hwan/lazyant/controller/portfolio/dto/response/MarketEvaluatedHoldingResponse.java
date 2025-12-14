package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.MarketEvaluatedHolding;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketEvaluatedHoldingResponse {
    private String name;
    private String symbol;
    private double principal;
    private double quantity;
    private double evaluatedAmount;
    private double profitLoss;
    private double profitLossRate;
    private double weight;

    public MarketEvaluatedHoldingResponse(MarketEvaluatedHolding marketEvaluatedHolding) {
        this.name = marketEvaluatedHolding.holding()
                .name();
        this.symbol = marketEvaluatedHolding.holding()
                .symbol();
        this.principal = marketEvaluatedHolding.holding()
                .principal();
        this.quantity = marketEvaluatedHolding.holding()
                .quantity();

        this.evaluatedAmount = marketEvaluatedHolding.evaluatedAmount();
        this.profitLoss = marketEvaluatedHolding.profitLoss();
        this.profitLossRate = marketEvaluatedHolding.profitLossRate();
        this.weight = marketEvaluatedHolding.weight();
    }
}
