package com.hwan.lazyant.controller.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    private Long id;
    private String name;
    private List<PortfolioItemResponse> items = new ArrayList<>();
}
