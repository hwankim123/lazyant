package com.hwan.lazyant.openapi.provider.koreainvestment.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwan.lazyant.config.KoreaInvestmentConfig;
import com.hwan.lazyant.openapi.auth.AccessKey;
import com.hwan.lazyant.openapi.auth.OpenApiAccessKeyManager;
import com.hwan.lazyant.openapi.common.OpenApiProvider;
import com.hwan.lazyant.openapi.market.MarketPriceProvider;
import com.hwan.lazyant.openapi.market.MarketPriceRequest;
import com.hwan.lazyant.openapi.market.MarketPriceResponse;
import com.hwan.lazyant.openapi.provider.koreainvestment.auth.GetAccessKeyRequest;
import com.hwan.lazyant.openapi.provider.koreainvestment.auth.GetAccessKeyResponse;
import com.hwan.lazyant.openapi.provider.koreainvestment.common.ApiKey;
import com.hwan.lazyant.openapi.provider.koreainvestment.common.ApiSpec;
import com.hwan.lazyant.openapi.provider.koreainvestment.common.KoreaInvestmentExcd;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KoreaInvestmentProvider implements MarketPriceProvider {

    private final ObjectMapper objectMapper;
    private final KoreaInvestmentConfig configuration;
    private final OpenApiAccessKeyManager openApiAccessKeyManager;

    @Override
    public MarketPriceResponse getMarketPrice(MarketPriceRequest request) {
        ApiSpec marketOrderApiSpec = this.configuration.getApiSpecByKey(ApiKey.GET_MARKET_PRICE_US);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("AUTH", "no-op");
        queryParams.put("SYMB", request.getSymbol());
        queryParams.put("EXCD", KoreaInvestmentExcd.from(request.getMarket()).getValue());

        String baseUrl = this.configuration.getBaseUrl();
        String url = baseUrl + marketOrderApiSpec.getUrl() + "?AUTH=" + queryParams.get("AUTH")
                + "&SYMB=" + queryParams.get("SYMB")
                + "&EXCD=" + queryParams.get("EXCD");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.getAccessKey().key());
        headers.set("appkey", this.configuration.getAppKey());
        headers.set("appsecret", this.configuration.getAppSecret());
        headers.set("tr_id", marketOrderApiSpec.getTrId());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((req, body, execution) -> {
            ClientHttpResponse response = execution.execute(req, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });
        GetMarketOrderDetailsResponse response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GetMarketOrderDetailsResponse.class
        ).getBody();
        return new MarketPriceResponse(Double.parseDouble(response.getOutput().getLast()));
    }

    private AccessKey getAccessKey() {
        Optional<AccessKey> accessKey = this.openApiAccessKeyManager.get(OpenApiProvider.KOREA_INVESTMENT);
        if (accessKey.isPresent() && !accessKey.get().isExpired()) {
            return accessKey.get();
        } else if (accessKey.isPresent() && accessKey.get().isExpired()) {
            this.openApiAccessKeyManager.expire(OpenApiProvider.KOREA_INVESTMENT);
        }

        ApiSpec getAccessKeyApiSpec = this.configuration.getApiSpecByKey(ApiKey.GET_ACCESS_KEY);
        String url = this.configuration.getBaseUrl() + getAccessKeyApiSpec.getUrl();
        String body;
        try {
            body = this.objectMapper.writeValueAsString(new GetAccessKeyRequest("client_credentials", this.configuration.getAppKey(), this.configuration.getAppSecret()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        GetAccessKeyResponse response = restTemplate.exchange(
                url,
                getAccessKeyApiSpec.getMethod(),
                entity,
                GetAccessKeyResponse.class
        ).getBody();
        return this.openApiAccessKeyManager.save(OpenApiProvider.KOREA_INVESTMENT,
                AccessKey.from(response.getAccessToken(), response.expireAt()));
    }
}
