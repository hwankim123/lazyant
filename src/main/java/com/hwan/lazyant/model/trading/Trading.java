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
    private Double quantity;

    @Nonnull
    @Column
    private LocalDateTime tradeAt;

    @Column
    private String memo;

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, 수량, 메모
    public Trading(@Nonnull Long stockId, @Nonnull Long accountId, @Nonnull TradingType tradingType,
                   @Nullable Double price, @Nullable Double quantity, @Nullable LocalDateTime tradeAt,
                   @Nullable String memo) {
        this.stockId = stockId;
        this.accountId = accountId;
        this.tradingType = tradingType;
        this.price = price;
        this.tradeAt = tradeAt == null ? LocalDateTime.now() : tradeAt;
        this.quantity = quantity;
        this.memo = memo;
    }

    public boolean hasQuantity() {
        return this.quantity != null;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    //TODO: 도메인 유의성이 없음(도메인 전제 조건) -> 가격, null check
    public void calculateQuantity(Double amount) {
        this.quantity = Math.round(amount / this.price * 1000000) / 1000000.0;
    }

    public boolean hasPrice() {
        return this.price != null;
    }

    public Double getPrice() {
        return this.price;
    }

    public void calculatePrice(Double amount) {
        this.price = Math.round(amount / this.quantity * 100) / 100.0;
    }

    public Long getStockId() {
        return this.stockId;
    }

    public Double getSignedQuantity() {
        return this.tradingType.applySign(this.quantity);
    }

    public double evaluateAmount() {
        return this.tradingType.applySign(Math.round(this.getQuantity() * this.price * 100) / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trading trading = (Trading) o;
        return Objects.equals(stockId, trading.stockId) && Objects.equals(accountId, trading.accountId) && tradingType == trading.tradingType && Objects.equals(price, trading.price) && Objects.equals(quantity, trading.quantity) && Objects.equals(tradeAt, trading.tradeAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, accountId, tradingType, price, quantity, tradeAt);
    }
}
