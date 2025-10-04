package com.hwan.lazyant.model.trading;

import com.hwan.lazyant.model.trading.TradingType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "la_tradings")
public class Trading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column
    private Long stockId;

    @Nonnull
    @Column
    private Long accountId;

    @Nonnull
    @Column
    private TradingType tradingType;

    @Nonnull
    @Column
    private Double marketCurrentPrice;

    @Column
    private Double volume;

    @Nonnull
    @Column
    private LocalDateTime tradeTime;

    @Column
    private String memo;

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, 수량, 메모
    public Trading(@Nonnull Long stockId, @Nonnull Long accountId, @Nonnull TradingType tradingType, @Nonnull Double marketCurrentPrice,
                   @Nullable LocalDateTime tradeTime, @Nullable Double volume, @Nullable String memo) {
        this.stockId = stockId;
        this.accountId = accountId;
        this.tradingType = tradingType;
        this.marketCurrentPrice = marketCurrentPrice;
        this.tradeTime = tradeTime == null ? LocalDateTime.now() : tradeTime;
        this.volume = volume;
        this.memo = memo;
    }

    public boolean hasVolume() {
        return this.volume != null;
    }

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, null check
    public void calculateVolume(Double tradePrice) {
        this.volume = Math.round(tradePrice / this.marketCurrentPrice) / 1000000.0;
    }
}
