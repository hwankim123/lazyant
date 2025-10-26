package com.hwan.lazyant.model.trading;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TradingEqualityTest {

    private static Stream<Arguments> tradingEqualityCases() {
        LocalDateTime now = LocalDateTime.now();

        return Stream.of(
                // 동등성 비교 대상: stockId, accountId, TradingType, price, volume, tradeAt
                Arguments.of(
                        new Trading(1L, 10L, TradingType.BUY, 100d, 1d, now, "memo"),
                        new Trading(1L, 10L, TradingType.BUY, 100d, 1d, now, "memo"),
                        true
                ),
                // stockId 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(2L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        false
                ),
                // accountId 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(1L, 2L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        false
                ),
                // TradingType 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(1L, 1L, TradingType.SELL, 100d, 0.5d, now, "memo"),
                        false
                ),
                // price 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(1L, 1L, TradingType.BUY, 200d, 0.5d, now, "memo"),
                        false
                ),
                // volume 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(1L, 1L, TradingType.BUY, 100d, 1.0d, now, "memo"),
                        false
                ),
                // tradeAt 다름 → false
                Arguments.of(
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now, "memo"),
                        new Trading(1L, 1L, TradingType.BUY, 100d, 0.5d, now.plusSeconds(1), "memo"),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("tradingEqualityCases")
    public void testEquality(Trading sut1, Trading sut2, boolean expectedEqual) {
        boolean result = sut1.equals(sut2);

        Assertions.assertThat(result).isEqualTo(expectedEqual);
    }
}
