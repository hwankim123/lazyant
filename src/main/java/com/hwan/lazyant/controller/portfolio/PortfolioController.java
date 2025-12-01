package com.hwan.lazyant.controller.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioHoldingResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioStatusResponse;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public void insert(@RequestBody PortfolioInsertRequest request) {
        portfolioService.insert(request);
    }

    @GetMapping("/my/target")
    public ResponseEntity<PortfolioResponse> getMyPortfolioTarget() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.getByUserId(1L)); // TODO: 개인화
    }

    @GetMapping("/my/actual")
    public ResponseEntity<PortfolioStatusResponse> getMyActualPortfolioStatus() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.getActualStatus(1L)); // TODO: 개인화
    }

    @GetMapping("/my/holdings")
    public ResponseEntity<List<PortfolioHoldingResponse>> getMyPortfolioHoldings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.getHoldingsByUserId(1L)); // TODO: 개인화
    }
}
