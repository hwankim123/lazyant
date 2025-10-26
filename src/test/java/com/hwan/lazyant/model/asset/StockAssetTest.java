package com.hwan.lazyant.model.asset;

import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StockAssetTest {

    @ParameterizedTest
    @CsvSource({
            "1, 1, BUY, 100d, 0.5d, 2025-10-23T22:00:00, null",
            "1, 1, SELL, 100d, 0.5d, 2025-10-23T22:00:00, null"
    })
    public void accumulate(Long stockId, Long accountId, TradingType tradingType, Double price, Double volume, LocalDateTime tradeAt, String memo) {
        Trading trading = new Trading(stockId, accountId, tradingType, price, volume, tradeAt, memo);
        StockAsset sut = new StockAsset(1L);
        double beforeAccumulatedVolume = sut.getVolume();
        double beforeAccumulatedInvestmentPrincipal = sut.getInvestmentPrincipal();

        sut.accumulate(trading);

        assertThat(sut.getVolume()).isEqualTo(beforeAccumulatedVolume + trading.getSignedVolume());
        assertThat(sut.getInvestmentPrincipal()).isEqualTo(beforeAccumulatedInvestmentPrincipal + trading.evaluateAmount());
    }
}