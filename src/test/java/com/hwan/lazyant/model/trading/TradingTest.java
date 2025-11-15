package com.hwan.lazyant.model.trading;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TradingTest {

    @Test
    public void calculate_volume() {
        Trading sut = createInstanceOf(100d, null);

        sut.calculateVolume(50d);

        assertThat(sut.hasVolume()).isTrue();
        assertThat(sut.getVolume()).isEqualTo(0.5d);
    }

    @Test
    public void calculate_price() {
        Trading sut = createInstanceOf(null, 0.5d);

        sut.calculatePrice(50d);

        assertThat(sut.hasPrice()).isTrue();
        assertThat(sut.getPrice()).isEqualTo(100d);
    }

    @ParameterizedTest
    @CsvSource({
            "BUY,  50.0",
            "SELL, -50.0"
    })
    void evaluate_amount_of_trading(TradingType type, double expectedAmount) {
        Trading sut = createInstanceOf(type, 100d, 0.5d);

        double amount = sut.evaluateAmount();

        assertThat(amount).isEqualTo(expectedAmount);
    }

    public static Trading createInstanceOf(Double price, Double volume) {
        return createInstanceOf(1L, TradingType.BUY, price, volume);
    }

    public static Trading createInstanceOf(TradingType type, Double price, Double volume) {
        return createInstanceOf(1L, type, price, volume);
    }

    public static Trading createInstanceOf(Long stockId, Double price, Double volume) {
        return createInstanceOf(stockId, TradingType.BUY, price, volume);
    }

    public static Trading createInstanceOf(Long stockId, TradingType type, Double price, Double volume) {
        return new Trading(stockId, 1L, type, price, volume, LocalDateTime.now(), "memo");
    }
}