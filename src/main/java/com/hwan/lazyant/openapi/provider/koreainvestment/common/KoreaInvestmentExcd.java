package com.hwan.lazyant.openapi.provider.koreainvestment.common;

import com.hwan.lazyant.model.stock.type.Market;

import java.util.Arrays;

public enum KoreaInvestmentExcd {
    HKS("HKS", "홍콩", Market.UNKNOWN),
    NYS("NYS", "뉴욕", Market.NYSE),
    NAS("NAS", "나스닥", Market.NASDAQ),
    AMS("AMS", "아멕스", Market.AMEX),
    TSE("TSE", "도쿄", Market.UNKNOWN),
    SHS("SHS", "상해", Market.UNKNOWN),
    SZS("SZS", "심천", Market.UNKNOWN),
    SHI("SHI", "상해지수", Market.UNKNOWN),
    SZI("SZI", "심천지수", Market.UNKNOWN),
    HSX("HSX", "호치민", Market.UNKNOWN),
    HNX("HNX", "하노이", Market.UNKNOWN),
    BAY("BAY", "뉴욕(주간)", Market.UNKNOWN),
    BAQ("BAQ", "나스닥(주간)", Market.UNKNOWN),
    BAA("BAA", "아멕스(주간)", Market.UNKNOWN);

    private final String value;
    private final String description;
    private final Market market;

    KoreaInvestmentExcd(String value, String description, Market market) {
        this.value = value;
        this.description = description;
        this.market = market;
    }

    public String getValue() {
        return this.value;
    }

    public static KoreaInvestmentExcd from(Market market) {
        return Arrays.stream(values())
                .filter(excd -> excd.market.equals(market))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown market: %s".formatted(market)));
    }
}
