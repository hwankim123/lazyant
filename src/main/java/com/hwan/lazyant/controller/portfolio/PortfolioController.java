package com.hwan.lazyant.controller.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioDetailResponse;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public void insert(@RequestBody PortfolioInsertRequest request) {
        portfolioService.insert(request);
    }

    @GetMapping("/my")
    public ResponseEntity<PortfolioDetailResponse> getMyPortfolio() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.getByUserId(1L)); // TODO: 개인화
    }
}
