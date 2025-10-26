package com.hwan.lazyant.model.trading;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TradingTest {

    @Test
    public void calculate_volume() {
        Trading sut = new Trading(1L, 1L, TradingType.BUY, 100d, null, LocalDateTime.now(), "memo");

        sut.calculateVolume(50d);

        assertThat(sut.hasVolume()).isTrue();
        assertThat(sut.getVolume()).isEqualTo(0.5d);
    }

    @Test
    public void calculate_price() {
        Trading sut = new Trading(1L, 1L, TradingType.BUY, null, 0.5d, LocalDateTime.now(), "memo");

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
        // given
        Trading sut = new Trading(1L, 1L, type, 100d, 0.5d, LocalDateTime.now(), "memo");

        // when
        double amount = sut.evaluateAmount();

        // then
        assertThat(amount).isEqualTo(expectedAmount);
    }
}