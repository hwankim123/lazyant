package com.hwan.lazyant.openapi.market;

/**
 * 현재가 조회 제공 인터페이스
 */
public interface MarketPriceProvider {

    MarketPriceResponse getMarketPrice(MarketPriceRequest request);
}
