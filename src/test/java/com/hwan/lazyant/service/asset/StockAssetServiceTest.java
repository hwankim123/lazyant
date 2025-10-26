package com.hwan.lazyant.service.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItem;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingType;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockAssetServiceTest {

    @Mock
    private StockAssetRepository stockAssetRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    //TODO: mock을 많이 쓰면 쓸수록 짜고치는것 같음. Repository만 mocking했는데도..
    @Test
    public void init_if_stockAsset_not_present() {
        Trading trading = new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, LocalDateTime.now(), "memo");
        StockAsset expected = StockAsset.from(trading);
        when(stockAssetRepository.findByStockId(anyLong())).thenReturn(Optional.empty());
        when(stockAssetRepository.save(any(StockAsset.class))).thenReturn(expected);
        when(portfolioRepository.findByUserId(anyLong())).thenReturn(Optional.of(new Portfolio(
                        "name",
                        List.of(new PortfolioItem(Factor.INDEX, 100), new PortfolioItem(Factor.UNKNOWN, 0))
        )));
        StockAssetService sut = new StockAssetService(stockAssetRepository, portfolioService);

        StockAsset stockAsset = sut.accumulate(trading);

        assertThat(stockAsset.getVolume()).isEqualTo(0.5d);
        assertThat(stockAsset.getInvestmentPrincipal()).isEqualTo(50d); // 100d * 0.5d
    }

}