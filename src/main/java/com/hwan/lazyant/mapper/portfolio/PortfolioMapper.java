package com.hwan.lazyant.mapper.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioItemInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioItemResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioResponse;
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

    public static PortfolioResponse mapToDetailResponse(Portfolio portfolio) {
        return new PortfolioResponse(portfolio.getId(), portfolio.getName(), portfolio.getItems().stream()
                .map(item -> new PortfolioItemResponse(item.getId(), item.getFactor(), item.getWeight()))
                .toList());
    }
}
