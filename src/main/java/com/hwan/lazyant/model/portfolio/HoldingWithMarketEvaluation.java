package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.Getter;

@Getter
//TODO: 안쓰이는 클래스
public class HoldingWithMarketEvaluation {
    private String name;
    private String symbol;
    private Market market;
    private double totalInvestmentPrincipal;
    private double totalQuantity;
    private double averagePrice;
    private double marketPrice;
    private double profitLoss;
    private double profitLossRate;

    public HoldingWithMarketEvaluation(String symbol, Market market, double totalInvestmentPrincipal, double totalQuantity) {
        this.symbol = symbol;
        this.market = market;
        this.totalInvestmentPrincipal = totalInvestmentPrincipal;
        this.totalQuantity = totalQuantity;
        this.averagePrice = this.calculateAveragePrice();
    }

    private double calculateAveragePrice() {
        return Math.round(this.totalInvestmentPrincipal / this.totalQuantity * 100) / 100.0;
    }

    public HoldingWithMarketEvaluation withMarketEvaluation(double marketPrice) {
        this.marketPrice = marketPrice;
        this.profitLoss = this.totalInvestmentPrincipal - (marketPrice * totalQuantity);
        this.profitLossRate = Math.round((this.profitLoss / totalInvestmentPrincipal) * 100 * 100) / 100.0;
        return this;
    }
}
