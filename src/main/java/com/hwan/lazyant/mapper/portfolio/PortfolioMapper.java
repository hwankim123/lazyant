package com.hwan.lazyant.mapper.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioItemInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioDetailResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioHoldingResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioItemDetailResponse;
import com.hwan.lazyant.model.portfolio.Holding;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItem;

import java.util.List;

public class PortfolioMapper {

    public static Portfolio mapToPortfolio(PortfolioInsertRequest request) {
        return Portfolio.withUnknownFactor(request.getName(), mapToPortfolioItems(request.getItems()));
    }

    private static List<PortfolioItem> mapToPortfolioItems(List<PortfolioItemInsertRequest> itemRequests) {
        return itemRequests.stream()
                .map(request -> new PortfolioItem(request.getFactor(), request.getWeight()))
                .toList();
    }

    public static PortfolioDetailResponse mapToDetailResponse(Portfolio portfolio) {
        return new PortfolioDetailResponse(portfolio.getId(), portfolio.getName(), portfolio.getItems().stream()
                .map(item -> new PortfolioItemDetailResponse(item.getId(), item.getFactor(), item.getWeight()))
                .toList());
    }

    public static PortfolioHoldingResponse mapToHoldingsResponse(Holding holding) {
        return new PortfolioHoldingResponse(
                holding.getMarket(),
                holding.getSymbol(),
                holding.getTotalInvestmentPrincipal(),
                holding.getTotalVolume(),
                holding.getAveragePrice(),
                holding.getMarketPrice(),
                holding.getProfitLoss(),
                holding.getProfitLossRate()
        );
    }
}
