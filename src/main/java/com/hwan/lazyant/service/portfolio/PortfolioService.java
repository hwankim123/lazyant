package com.hwan.lazyant.service.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioSnapshotResponse;
import com.hwan.lazyant.mapper.portfolio.PortfolioMapper;
import com.hwan.lazyant.model.portfolio.MarketEvaluatedHolding;
import com.hwan.lazyant.model.portfolio.MarketEvaluatedHolding.Holding;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItemSnapshot;
import com.hwan.lazyant.model.portfolio.PortfolioSnapshot;
import com.hwan.lazyant.openapi.market.MarketPriceProvider;
import com.hwan.lazyant.openapi.market.MarketPriceRequest;
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

    public Portfolio findByUserId(long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Portfolio from repository. Given userId=%d".formatted(userId)));
    }

    /**
     * 포트폴리오 현황 정보 생성
     */
    public PortfolioSnapshotResponse generatePortfolioSnapshot(long userId) {
        List<Holding> holdings = portfolioRepository.findHoldingsByUserId(userId).stream()
                .map(HoldingProjection::toModel)
                .toList();
        List<MarketEvaluatedHolding> marketEvaluatedHoldings = holdings.stream()
                .map(holding -> holding.evaluate(
                        marketPriceProvider.getMarketPrice(new MarketPriceRequest(holding.market(), holding.symbol()))
                                .getUsdMarketPrice() // TODO: 국내 주식 반영
                        )
                ).toList();

        Map<Long, PortfolioItemSnapshot> itemSnapshotMap = this.aggregateItemSnapshots(marketEvaluatedHoldings);
        return new PortfolioSnapshotResponse(PortfolioSnapshot.gatherAll(itemSnapshotMap.values()));
    }

    /**
     * 주식시장이 반영된 구성 종목 현황을 포트폴리오 항목별 현황으로 aggregate
     */
    private Map<Long, PortfolioItemSnapshot> aggregateItemSnapshots(List<MarketEvaluatedHolding> marketEvaluatedHoldings) {
        Map<Long, PortfolioItemSnapshot> itemSnapshotMap = new HashMap<>();
        for (MarketEvaluatedHolding marketEvaluatedHolding : marketEvaluatedHoldings) {
            if (itemSnapshotMap.containsKey(marketEvaluatedHolding.getPortfolioItemId())) {
                PortfolioItemSnapshot portfolioItemSnapshot = itemSnapshotMap.get(marketEvaluatedHolding.getPortfolioItemId());
                portfolioItemSnapshot.accumulate(marketEvaluatedHolding);
            } else {
                itemSnapshotMap.put(marketEvaluatedHolding.getPortfolioItemId(), new PortfolioItemSnapshot(marketEvaluatedHolding));
            }
        }
        return itemSnapshotMap;
    }
}
