package com.hwan.lazyant.controller.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDetailResponse {
    private Long id;
    private String name;
    private List<PortfolioItemDetailResponse> items = new ArrayList<>();
}
