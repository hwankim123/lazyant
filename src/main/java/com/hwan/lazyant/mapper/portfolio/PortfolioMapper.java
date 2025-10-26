package com.hwan.lazyant.mapper.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.PortfolioItemInsertRequest;
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
}
