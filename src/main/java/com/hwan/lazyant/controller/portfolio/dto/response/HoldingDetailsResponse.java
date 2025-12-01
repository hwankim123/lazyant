package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.Factor;

public class HoldingDetailsResponse {
    private String name;
    private String symbol;
    private Factor factor;

    private double totalQuantity; // 총 수량
    private double totalInvestmentPrincipal; // 투자 원금
    private double averagePrice; // 평단가

    private double evaluatedAmount; // 평가금액
    private double marketPrice; // 시장가
    private double profitLoss; // 총 수익금
    private double profitLossRate; // 수익률
    private float weight; // 비중
}
