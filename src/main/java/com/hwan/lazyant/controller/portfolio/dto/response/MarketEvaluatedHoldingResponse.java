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
    private Long stockAssetId;
    private double principal;
    private double quantity;
    private double evaluatedAmount;
    private double profitLoss;
    private double profitLossRate;
    private double weight;

    public MarketEvaluatedHoldingResponse(MarketEvaluatedHolding marketEvaluatedHolding) {
        this.name = marketEvaluatedHolding.getName();
        this.symbol = marketEvaluatedHolding.getSymbol();
        this.stockAssetId = marketEvaluatedHolding.getStockAssetId();
        this.principal = marketEvaluatedHolding.getPrincipal();
        this.quantity = marketEvaluatedHolding.getQuantity();

        this.evaluatedAmount = marketEvaluatedHolding.getEvaluatedAmount();
        this.profitLoss = marketEvaluatedHolding.getProfitLoss();
        this.profitLossRate = marketEvaluatedHolding.getProfitLossRate();
        this.weight = marketEvaluatedHolding.getWeight();
    }
}
