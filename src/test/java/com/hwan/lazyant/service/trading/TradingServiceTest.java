package com.hwan.lazyant.service.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.mapper.trading.TradingMapper;
import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingType;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import com.hwan.lazyant.repository.trading.TradingRepository;
import com.hwan.lazyant.service.asset.StockAssetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradingServiceTest {

    @Mock
    private TradingRepository tradingRepository;
    @InjectMocks
    private StockAssetService stockAssetService;
    @Mock
    private StockAssetRepository stockAssetRepository;

    @Test
    public void calculate_volume_if_request_dont_have_volume() {
        LocalDateTime tradeAt = LocalDateTime.now();
        Trading expected = TradingMapper.mapToTrading(new TradingInsertRequest(1L, 1L, TradingType.BUY, 100d, 0.5d, 50d, "memo", tradeAt));
        when(stockAssetRepository.findByStockId(1L)).thenReturn(Optional.of(StockAsset.from(expected)));
        TradingInsertRequest tradingInsertRequest = new TradingInsertRequest(1L, 1L, TradingType.BUY, 100d, null, 50d, "memo", tradeAt);
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Trading trading = sut.write(tradingInsertRequest);

        assertThat(trading).isEqualTo(expected);
    }

    @Test
    public void calculate_price_if_request_dont_have_price() {
        LocalDateTime tradeAt = LocalDateTime.now();
        Trading expected = TradingMapper.mapToTrading(new TradingInsertRequest(1L, 1L, TradingType.BUY, 100d, 0.5d, 50d, "memo", tradeAt));
        when(stockAssetRepository.findByStockId(1L)).thenReturn(Optional.of(StockAsset.from(expected)));
        TradingInsertRequest tradingInsertRequest = new TradingInsertRequest(1L, 1L, TradingType.BUY, 100d, 0.5d, null, "memo", tradeAt);
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Trading trading = sut.write(tradingInsertRequest);

        assertThat(trading).isEqualTo(expected);
    }

    @Test
    public void request_dont_have_volume_and_price_is_invalid() {
        TradingInsertRequest tradingInsertRequest = new TradingInsertRequest(1L, 1L, TradingType.BUY, null, null, 50d, "memo", LocalDateTime.now());
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.write(tradingInsertRequest));
    }
}