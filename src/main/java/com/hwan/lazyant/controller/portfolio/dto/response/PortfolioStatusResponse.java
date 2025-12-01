package com.hwan.lazyant.controller.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * 포트폴리오 현황 response dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioStatusResponse {
    private double totalAmount;
    private double totalProfitLoss; // 총 수익금
    private double totalProfitLossRate; // 수익률
    private List<PortfolioItemStatusResponse> statusOfItems;

    public PortfolioStatusResponse(Collection<PortfolioItemStatusResponse> itemStatusList) {
        this.statusOfItems = itemStatusList.stream().toList();

        double totalPrincipal = 0.0;
        for(PortfolioItemStatusResponse itemStatus : itemStatusList) {
            itemStatus.calculateTotalProfitLoss();
            totalPrincipal += itemStatus.getTotalPrincipal();
            this.totalAmount += itemStatus.getTotalAmount();
        }
        itemStatusList.forEach(itemStatus -> itemStatus.calculateWeight(this.totalAmount));
        this.totalProfitLoss = totalAmount - totalPrincipal;
        this.totalProfitLossRate = ((this.totalProfitLoss / totalPrincipal)) * 100 * 100 / 100.0;
    }
}
