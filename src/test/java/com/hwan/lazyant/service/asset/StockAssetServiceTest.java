package com.hwan.lazyant.service.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItem;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingTest;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
        Trading trading = TradingTest.createInstanceOf(100d, 0.5d);
        StockAsset expected = StockAsset.from(trading);
        when(stockAssetRepository.findByStockId(anyLong())).thenReturn(Optional.empty());
        when(stockAssetRepository.save(any(StockAsset.class))).thenReturn(expected);
        //TODO: 포트폴리오 테스트 짜면서 팩토리 클래스 생성하면 여기에 사용하기
        ArrayList<PortfolioItem> items = new ArrayList<>();
        items.add(new PortfolioItem(Factor.INDEX, 100));
        Portfolio portfolio = Portfolio.withUnknownFactor("name", items);
        when(portfolioRepository.findByUserId(anyLong())).thenReturn(Optional.of(portfolio));
        StockAssetService sut = new StockAssetService(stockAssetRepository, portfolioService);

        StockAsset stockAsset = sut.accumulate(trading);

        assertThat(stockAsset.getVolume()).isEqualTo(0.5d);
        assertThat(stockAsset.getInvestmentPrincipal()).isEqualTo(100d * 0.5d);
    }

}