package com.hwan.lazyant.model.trading;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column
    private Double price;

    @Column
    private Double volume;

    @Nonnull
    @Column
    private LocalDateTime tradeTime;

    @Column
    private String memo;

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, 수량, 메모
    public Trading(@Nonnull Long stockId, @Nonnull Long accountId, @Nonnull TradingType tradingType,
                   @Nullable Double price, @Nullable LocalDateTime tradeTime, @Nullable Double volume,
                   @Nullable String memo) {
        this.stockId = stockId;
        this.accountId = accountId;
        this.tradingType = tradingType;
        this.price = price;
        this.tradeTime = tradeTime == null ? LocalDateTime.now() : tradeTime;
        this.volume = volume;
        this.memo = memo;
    }

    public boolean hasVolume() {
        return this.volume != null;
    }

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, null check
    public void calculateVolume(Double amount) {
        this.volume = Math.round(amount / this.price * 1000000) / 1000000.0;
    }

    public boolean hasPrice() {
        return this.price != null;
    }

    public void calculatePrice(Double tradePrice) {
        this.price = Math.round(tradePrice / this.volume * 100) / 100.0;
    }

    public Long getStockId() {
        return this.stockId;
    }

    public Double provideSignedVolume() {
        return this.tradingType.applySign(this.volume);
    }

    public double calculateAmount() {
        return Math.round(this.provideSignedVolume() * this.price * 100) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trading trading = (Trading) o;
        return Objects.equals(stockId, trading.stockId) && Objects.equals(accountId, trading.accountId) && tradingType == trading.tradingType && Objects.equals(price, trading.price) && Objects.equals(volume, trading.volume) && Objects.equals(tradeTime, trading.tradeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, accountId, tradingType, price, volume, tradeTime);
    }
}
