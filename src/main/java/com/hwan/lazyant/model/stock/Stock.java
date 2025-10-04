package com.hwan.lazyant.model.stock;

import com.hwan.lazyant.model.stock.type.EtfType;
import com.hwan.lazyant.model.stock.type.Market;
import com.hwan.lazyant.model.stock.type.StockType;
import jakarta.persistence.*;

@Entity
@Table(name = "la_stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Market market;

    private String symbol;

    private EtfType etfType;

    private StockType type;

    public Stock(String name, Market market, String symbol, EtfType etfType, StockType type) {
        this.name = name;
        this.market = market;
        this.symbol = symbol;
        this.etfType = etfType;
        this.type = type;
    }
}
