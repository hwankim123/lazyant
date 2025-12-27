package com.hwan.lazyant.controller.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.request.PortfolioInsertRequest;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioResponse;
import com.hwan.lazyant.controller.portfolio.dto.response.PortfolioSnapshotResponse;
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

    /**
     * 포트폴리오 생성
     */
    @PostMapping
    public void insert(@RequestBody PortfolioInsertRequest request) {
        portfolioService.insert(request);
    }

    /**
     * 내 포트폴리오 조회
     */
    @GetMapping("/my/target")
    public ResponseEntity<PortfolioResponse> getMyPortfolioTarget() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.getByUserId(1L)); // TODO: 개인화
    }

    /**
     * 내 포트폴리오 현황 조회
     */
    @GetMapping("/my/snapshot")
    public ResponseEntity<PortfolioSnapshotResponse> generatePortfolioSnapshot() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioService.generatePortfolioSnapshot(1L)); // TODO: 개인화
    }
}
