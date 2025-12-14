package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.util.StockValueCalculator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 포트폴리오 항목 현황
 */
@Getter
public class PortfolioItemSnapshot {
    private final Long id;
    private final Factor factor;
    private double principal; // 투자 원금
    private double evaluatedAmount; // 평가금액
    private double profitLoss; // 수익/손익금
    private double profitLossRate; // 수익/손익률
    private double weight; // 포트폴리오 내 비중
    private List<MarketEvaluatedHolding> holdings = new ArrayList<>();

    public PortfolioItemSnapshot(MarketEvaluatedHolding marketEvaluatedHolding) {
        this.id = marketEvaluatedHolding.getPortfolioItemId();
        this.factor = marketEvaluatedHolding.getFactor();
        this.principal = marketEvaluatedHolding.getPrincipal();
        this.evaluatedAmount = marketEvaluatedHolding.getEvaluatedAmount();
        this.holdings.add(marketEvaluatedHolding);
    }

    public void accumulate(MarketEvaluatedHolding marketEvaluatedHolding) {
        this.principal += marketEvaluatedHolding.getPrincipal();
        this.evaluatedAmount += marketEvaluatedHolding.getEvaluatedAmount();
        this.holdings.add(marketEvaluatedHolding);
    }


    public void calculateProfitLossAndRate() {
        this.profitLoss = StockValueCalculator.calculateProfitLoss(evaluatedAmount, principal);
        this.profitLossRate = StockValueCalculator.calculateProfitLossRate(evaluatedAmount, principal, 2);
    }

    public void calculateWeight(double totalAmount) {
        this.weight = StockValueCalculator.calculateWeight(this.evaluatedAmount, totalAmount, 2);
        this.holdings.forEach(holding -> holding.calculateWeight(totalAmount));
    }
}
