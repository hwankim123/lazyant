package com.hwan.lazyant.controller.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.PortfolioInsertRequest;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public void insert(@RequestBody PortfolioInsertRequest request) {
        portfolioService.insert(request);
    }
}
