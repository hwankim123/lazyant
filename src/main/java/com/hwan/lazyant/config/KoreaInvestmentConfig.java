package com.hwan.lazyant.config;

import com.hwan.lazyant.openapi.provider.koreainvestment.common.ApiKey;
import com.hwan.lazyant.openapi.provider.koreainvestment.common.ApiSpec;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "open-api.korea-investment")
public class KoreaInvestmentConfig {
    private String appKey;
    private String appSecret;
    private String baseUrl;
    private Map<ApiKey, ApiSpec> apiSpecs;

    public ApiSpec getApiSpecByKey(ApiKey apiKey) {
        return apiSpecs.entrySet().stream()
                .filter(entry -> entry.getKey().equals(apiKey))
                .findAny()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new IllegalArgumentException("unknown apiSpec"));
    }
}
