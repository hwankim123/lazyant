package com.hwan.lazyant.service.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioSnapshotResponse;
import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.stock.type.Market;
import com.hwan.lazyant.openapi.market.MarketPriceProvider;
import com.hwan.lazyant.openapi.market.MarketPriceRequest;
import com.hwan.lazyant.openapi.market.MarketPriceResponse;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import com.hwan.lazyant.repository.portfolio.projection.HoldingProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private MarketPriceProvider marketPriceProvider;

    @Test
    void getPortfolioSnapshot() {
        //given
        when(portfolioRepository.findHoldingsByUserId(anyLong())).thenReturn(
                List.of(
                        new HoldingProjectionForTest("invesco qqq", Market.NASDAQ, "QQQ", 1L, Factor.GROWTH, 1L, 1000.0, 2),
                        new HoldingProjectionForTest("VTV", Market.NYSE, "VTV", 2L, Factor.VALUE, 2L, 600.0, 3)
                )
        );
        lenient().when(marketPriceProvider.getMarketPrice(new MarketPriceRequest(Market.NASDAQ, "QQQ"))).thenReturn(new MarketPriceResponse(1000.0));
        lenient().when(marketPriceProvider.getMarketPrice(new MarketPriceRequest(Market.NYSE, "VTV"))).thenReturn(new MarketPriceResponse(400.0));

        PortfolioService sut = new PortfolioService(portfolioRepository, marketPriceProvider);

        //when
        PortfolioSnapshotResponse response = sut.generatePortfolioSnapshot(1L);

        //then
        //TODO: 현재 double의 부동소수점 방식으로 인해 정확한 값비교가 안됨
        assertThat(response)
                .extracting("totalAmount", "totalProfitLoss")
                .containsExactly(3200.0, 1600.0);
        assertThat(response.getItemSnapshots())
                .extracting("principal", "evaluatedAmount", "profitLoss")
                .containsExactly(
                        tuple(1000.0, 2000.0, 1000.0),
                        tuple(600.0, 1200.0, 600.0)
                );
    }

    public static class HoldingProjectionForTest implements HoldingProjection {

        private final String name;
        private final Market market;
        private final String symbol;
        private final long portfolioItemId;
        private final Factor factor;
        private final Long stockAssetId;
        private final double principal;
        private final int quantity;

        public HoldingProjectionForTest(String name, Market market, String symbol, long portfolioItemId, Factor factor,
                                        Long stockAssetId, double principal, int quantity) {
            this.name = name;
            this.market = market;
            this.symbol = symbol;
            this.portfolioItemId = portfolioItemId;
            this.factor = factor;
            this.stockAssetId = stockAssetId;
            this.principal = principal;
            this.quantity = quantity;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Market getMarket() {
            return this.market;
        }

        @Override
        public String getSymbol() {
            return this.symbol;
        }

        @Override
        public Long getPortfolioItemId() {
            return this.portfolioItemId;
        }

        @Override
        public Factor getFactor() {
            return this.factor;
        }

        @Override
        public Long getStockAssetId() {
            return stockAssetId;
        }

        @Override
        public double getPrincipal() {
            return this.principal;
        }

        @Override
        public double getQuantity() {
            return this.quantity;
        }
    }
}