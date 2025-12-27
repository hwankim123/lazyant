package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.model.stock.type.Market;
import com.hwan.lazyant.util.StockValueCalculator;
import lombok.RequiredArgsConstructor;

/**
 * 시장 가치가 반영된 주식 투자 구성종목
 */
@RequiredArgsConstructor
public class MarketEvaluatedHolding {
    private final Holding holding;
    private final double evaluatedAmount; // 평가금액
    private final double profitLoss; // 수익/손익금
    private final double profitLossRate; // 수익/손익률
    private double weight; // 전체 포트폴리오 내 비중

    public String getName() {
        return holding.name();
    }

    public Long getPortfolioItemId() {
        return holding.portfolioItemId();
    }

    public Factor getFactor() {
        return holding.factor();
    }

    public String getSymbol() {
        return holding.symbol();
    }

    public Long getStockAssetId() {
        return holding.stockAssetId();
    }

    public double getPrincipal() {
        return holding.principal();
    }

    public double getQuantity() {
        return holding.quantity();
    }

    public double getEvaluatedAmount() {
        return evaluatedAmount;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public double getProfitLossRate() {
        return profitLossRate;
    }

    public void calculateWeight(double totalAmount) {
        this.weight = StockValueCalculator.calculateWeight(evaluatedAmount, totalAmount, 2);
    }

    public double getWeight() {
        return weight;
    }

    /**
     * 주식 투자 구성종목
     */
    public record Holding(
            String name,
            Market market,
            String symbol,
            Long portfolioItemId,
            Factor factor,
            Long stockAssetId,
            double principal, // 투자 원금
            double quantity // 보유 수량
    ) {

        public MarketEvaluatedHolding evaluate(double marketPrice) {
            return new MarketEvaluatedHolding(
                    this,
                    this.evaluateEvaluatedAmount(marketPrice),
                    this.evaluateProfitLoss(marketPrice),
                    this.evaluateProfitLossRate(marketPrice)
            );
        }

        private double evaluateEvaluatedAmount(double marketPrice) {
            return StockValueCalculator.calculateAmount(marketPrice, quantity, 2);
        }

        private double evaluateProfitLoss(double marketPrice) {
            return StockValueCalculator.calculateProfitLoss(this.evaluateEvaluatedAmount(marketPrice), principal);
        }

        private double evaluateProfitLossRate(double marketPrice) {
            return StockValueCalculator.calculateProfitLossRate(this.evaluateEvaluatedAmount(marketPrice), principal, 2);
        }
    }
}
