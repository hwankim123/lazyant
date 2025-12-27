package com.hwan.lazyant.facade;

import com.hwan.lazyant.controller.asset.dto.response.MarketEvaluatedStockAssetDetailsResponse;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.openapi.market.MarketPriceProvider;
import com.hwan.lazyant.openapi.market.MarketPriceRequest;
import com.hwan.lazyant.repository.asset.projection.MarketEvaluatedStockAssetDetails;
import com.hwan.lazyant.service.asset.StockAssetService;
import com.hwan.lazyant.service.trading.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAssetFacade {

    private final StockAssetService stockAssetService;
    private final TradingService tradingService;
    private final MarketPriceProvider marketPriceProvider;

    public MarketEvaluatedStockAssetDetailsResponse getStockAssetDetails(Long stockAssetId) {
        MarketEvaluatedStockAssetDetails.StockAssetDetails stockAssetDetails = stockAssetService.findStockAssetDetailsById(stockAssetId)
                .toModel();
        double usdMarketPrice = marketPriceProvider.getMarketPrice(
                new MarketPriceRequest(
                        stockAssetDetails.market(),
                        stockAssetDetails.symbol()
                )
        ).getUsdMarketPrice();// TODO: 국내 주식 반영
        MarketEvaluatedStockAssetDetails marketEvaluatedStockAssetDetails = stockAssetDetails.evaluate(usdMarketPrice);

        List<Trading> recentTradings = tradingService.getRecentTradings(1L, stockAssetDetails.stockId()); // TODO: 개인화
        return new MarketEvaluatedStockAssetDetailsResponse(marketEvaluatedStockAssetDetails, recentTradings);
    }
}
