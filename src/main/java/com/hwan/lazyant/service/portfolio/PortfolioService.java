package com.hwan.lazyant.service.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioDetailResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioHoldingResponse;
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

import java.util.List;

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

    public PortfolioDetailResponse getByUserId(long userId) {
        return PortfolioMapper.mapToDetailResponse(this.findByUserId(userId));
    }

    public List<PortfolioHoldingResponse> getHoldingsByUserId(long userId) {
        List<Holding> holdings = portfolioRepository.findHoldingsByUserId(userId).stream()
                .map(HoldingProjection::toModel)
                .toList();
        // 현재가 조회 API call
        return holdings.stream()
                .map(holding -> {
                    MarketPriceResponse marketPriceResponse = marketPriceProvider.getMarketPrice(new MarketPriceRequest(holding.getMarket(), holding.getSymbol()));
                    return PortfolioMapper.mapToHoldingsResponse(
                            holding.withMarketEvaluation(marketPriceResponse.getUsdMarketPrice())
                    );
                })
                .toList();
    }

    public Portfolio findByUserId(long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Portfolio from repository. Given userId=%d".formatted(userId)));
    }
}
