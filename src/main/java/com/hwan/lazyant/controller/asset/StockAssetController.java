package com.hwan.lazyant.controller.asset;

import com.hwan.lazyant.controller.asset.dto.response.MarketEvaluatedStockAssetDetailsResponse;
import com.hwan.lazyant.facade.StockAssetFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/assets/stock")
@RestController
public class StockAssetController {

    private final StockAssetFacade stockAssetFacade;

    @GetMapping("/{stockAssetId}")
    public ResponseEntity<MarketEvaluatedStockAssetDetailsResponse> getStockAssetDetails(
            @PathVariable("stockAssetId") Long stockAssetId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(stockAssetFacade.getStockAssetDetails(stockAssetId));
    }
}
