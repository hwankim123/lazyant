package com.hwan.lazyant.service.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioHoldingResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioItemStatusResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioStatusResponse;
import com.hwan.lazyant.mapper.portfolio.PortfolioMapper;
import com.hwan.lazyant.model.portfolio.Holding;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.openapi.market.MarketPriceProvider;
import com.hwan.lazyant.openapi.market.MarketPriceRequest;
import com.hwan.lazyant.openapi.market.MarketPriceResponse;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import com.hwan.lazyant.repository.portfolio.projection.HoldingProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MarketPriceProvider marketPriceProvider;

    @Transactional
    public void insert(PortfolioInsertRequest request) {
        Portfolio portfolio = PortfolioMapper.mapToPortfolio(request);
        portfolio.setUserId(1L);//TODO: 개인화
        portfolioRepository.save(portfolio);
    }

    public PortfolioResponse getByUserId(long userId) {
        return PortfolioMapper.mapToDetailResponse(this.findByUserId(userId));
    }

    public PortfolioStatusResponse getActualStatus(long userId) {
        List<Holding> holdings = portfolioRepository.findHoldingsByUserId(userId).stream()
                .map(HoldingProjection::toModel)
                .toList();

        // 현재가 조회 API call
        List<PortfolioHoldingResponse> portfolioHoldings = holdings.stream()
                .map(holding -> {
                    MarketPriceResponse marketPriceResponse = marketPriceProvider.getMarketPrice(new MarketPriceRequest(holding.market(), holding.symbol()));
                    return new PortfolioHoldingResponse(holding, marketPriceResponse.getUsdMarketPrice());
                })
                .toList();

        Map<Long, PortfolioItemStatusResponse> itemStatusMap = new HashMap<>();

        for(PortfolioHoldingResponse portfolioHolding : portfolioHoldings) {
            if(itemStatusMap.containsKey(portfolioHolding.getPortfolioItemId())) {
                PortfolioItemStatusResponse itemStatus = itemStatusMap.get(portfolioHolding.getPortfolioItemId());
                itemStatus.accumulate(portfolioHolding);
            } else {
                itemStatusMap.put(portfolioHolding.getPortfolioItemId(), new PortfolioItemStatusResponse(portfolioHolding));
            }
        }

        return new PortfolioStatusResponse(itemStatusMap.values());
    }

    public List<PortfolioHoldingResponse> getHoldingsByUserId(long userId) {
        List<Holding> holdings = portfolioRepository.findHoldingsByUserId(userId).stream()
                .map(HoldingProjection::toModel)
                .toList();
        // 현재가 조회 API call
        return holdings.stream()
                .map(holding -> {
                    MarketPriceResponse marketPriceResponse = marketPriceProvider.getMarketPrice(new MarketPriceRequest(holding.market(), holding.symbol()));
                    return new PortfolioHoldingResponse(holding, marketPriceResponse.getUsdMarketPrice());
                })
                .toList();
    }

    public Portfolio findByUserId(long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Portfolio from repository. Given userId=%d".formatted(userId)));
    }
}
