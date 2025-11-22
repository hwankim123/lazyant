package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHoldingResponse {
    private Market market;
    private String symbol;
    private double totalInvestmentPrincipal; // 투자 원금
    private double totalVolume; // 총 수량
    private double averagePrice; // 평단가
    private double marketPrice; // 시장가
    private double profitLoss; // 총 수익금
    private double profitLossRate; // 수익률

}
