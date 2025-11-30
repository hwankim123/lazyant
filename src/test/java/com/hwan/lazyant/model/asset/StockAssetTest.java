package com.hwan.lazyant.model.asset;

import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingTest;
import com.hwan.lazyant.model.trading.TradingType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StockAssetTest {

    @ParameterizedTest
    @CsvSource({
            "BUY, 100d, 0.5d",
            "SELL, 100d, 0.5d"
    })
    public void accumulate(TradingType type, Double price, Double volume) {
        Trading trading = TradingTest.createInstanceOf(type, price, volume);
        StockAsset sut = new StockAsset(1L);
        double volumeBeforeAccumulated = sut.getVolume();
        double investmentPrincipalBeforeAccumulated = sut.getInvestmentPrincipal();

        sut.accumulate(trading);

        assertThat(sut.getVolume()).isEqualTo(volumeBeforeAccumulated + trading.getSignedQuantity());
        assertThat(sut.getInvestmentPrincipal()).isEqualTo(investmentPrincipalBeforeAccumulated + trading.evaluateAmount());
    }

    public static StockAsset createInstanceOf(double volume, double investmentPrincipal) {
        return new StockAsset(1L, 1L, volume, investmentPrincipal);
    }
}