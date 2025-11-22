package com.hwan.lazyant.openapi.provider.koreainvestment.common;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;

@Getter
@Setter
public class ApiSpec {
    private String name;
    private List<Market> markets;
    private HttpMethod method;
    private String url;
    private String trId;
}