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
import static org.mockito.ArgumentMatchers.anyLong;
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
        Trading expectedTrading = TradingMapper.mapToTrading(createInstanceOf(100d, 0.5d, 50d, tradeAt));
        when(stockAssetRepository.findByStockId(anyLong())).thenReturn(Optional.of(StockAsset.from(expectedTrading)));
        TradingInsertRequest tradingInsertRequest = createInstanceOf(100d, null, 50d, tradeAt);
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Trading trading = sut.write(tradingInsertRequest);

        assertThat(trading).isEqualTo(expectedTrading);
    }

    @Test
    public void calculate_price_if_request_dont_have_price() {
        LocalDateTime tradeAt = LocalDateTime.now();
        Trading expectedTrading = TradingMapper.mapToTrading(createInstanceOf(100d, 0.5d, 50d, tradeAt));
        when(stockAssetRepository.findByStockId(anyLong())).thenReturn(Optional.of(StockAsset.from(expectedTrading)));
        TradingInsertRequest tradingInsertRequest = createInstanceOf(null, 0.5d, 50d, tradeAt);
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Trading trading = sut.write(tradingInsertRequest);

        assertThat(trading).isEqualTo(expectedTrading);
    }

    @Test
    public void request_dont_have_volume_and_price_is_invalid() {
        TradingInsertRequest tradingInsertRequest = createInstanceOf(null, null, 50d, LocalDateTime.now());
        TradingService sut = new TradingService(tradingRepository, stockAssetService);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.write(tradingInsertRequest));
    }

    public static TradingInsertRequest createInstanceOf(Double price, Double volume, Double amount, LocalDateTime dateTime) {
        return createInstanceOf(TradingType.BUY, price, volume, amount, dateTime);
    }

    public static TradingInsertRequest createInstanceOf(TradingType type, Double price, Double volume, Double amount, LocalDateTime dateTime) {
        return new TradingInsertRequest(1L, 1L, type, price, volume, amount, "memo", dateTime);
    }
}