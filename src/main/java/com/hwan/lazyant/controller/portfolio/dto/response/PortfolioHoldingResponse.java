package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.Holding;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHoldingResponse {
    private String name;
    private String symbol;
    private Long portfolioItemId; // TODO : 얘네는 조회되면 데이터중복임. 근데 애초에 이 클래스 자체는 비즈니스 로직을 수행하는 중요 개념임. 그래서 DTO에는 필드 삭제로 대응하고 ,새로 만들 개념클래스에서는 유지
    private Factor factor; // TODO : 얘네는 조회되면 데이터중복임
    private double principal;
    private double quantity; // 수량

    private double evaluatedAmount; // 평가금액
    private double profitLoss; // 수익금
    private double profitLossRate; // 수익률
    private double weight; // 비중

    public PortfolioHoldingResponse(Holding holding, double usdMarketPrice) {
        this.name = holding.name();
        this.symbol = holding.symbol();
        this.portfolioItemId = holding.portfolioItemId();
        this.factor = holding.factor();
        this.principal = holding.principal();
        this.quantity = holding.quantity();
        this.evaluatedAmount = holding.evaluateEvaluatedAmount(usdMarketPrice);
        this.profitLoss = holding.evaluateProfitLoss(usdMarketPrice);
        this.profitLossRate = holding.evaluateProfitLossRate(usdMarketPrice);
    }

    public void calculateWeight(double totalAmount) {
        this.weight = ((this.evaluatedAmount / totalAmount) * 100 * 100) / 100.0;
    }
}
