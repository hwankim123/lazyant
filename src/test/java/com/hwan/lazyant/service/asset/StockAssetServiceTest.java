package com.hwan.lazyant.service.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.asset.StockAssetTest;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingTest;
import com.hwan.lazyant.portfolio.PortfolioTest;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockAssetServiceTest {

    @Mock
    private StockAssetRepository stockAssetRepository;
    @InjectMocks
    private PortfolioService portfolioService;
    @Mock
    private PortfolioRepository portfolioRepository;

    private static Stream<Arguments> accumulateTestArguments() {
        return Stream.of(
                // 신규 진입한 주식 매매 기록의 stockId는 404L이라고 가정
                Arguments.of(100d, 0.5d, 404L, Optional.empty(), 0.5d, 50d),
                // 기존 보유한 주식 자산의 주식 매매 기록의 stockId는 1L이라고 가정
                Arguments.of(100d, 0.5d, 1L, Optional.of(StockAssetTest.createInstanceOf(5d, 100d)), 5.5d, 150d)
        );
    }

    @ParameterizedTest
    @MethodSource("accumulateTestArguments")
    public void accumulate_trading_into_stock_asset(double tradingPrice, double tradingVolume, Long stockId, Optional<StockAsset> optionalStockAsset,
                                                    double stockAssetVolume, double stockAssetInvestmentPrincipal) {
        Trading trading = TradingTest.createInstanceOf(stockId, tradingPrice, tradingVolume);
        when(stockAssetRepository.findByStockId(stockId)).thenReturn(optionalStockAsset);
        lenient().when(portfolioRepository.findByUserId(anyLong())).thenReturn(Optional.of(PortfolioTest.createIndexOnlyPortfolio()));
        StockAssetService sut = new StockAssetService(stockAssetRepository, portfolioService);

        StockAsset stockAsset = sut.accumulate(trading);

        assertThat(stockAsset.getVolume()).isEqualTo(stockAssetVolume);
        assertThat(stockAsset.getPrincipal()).isEqualTo(stockAssetInvestmentPrincipal);
    }

}