package com.hwan.lazyant.openapi.provider.koreainvestment.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccessKeyRequest {

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("appkey")
    private String appKey;

    @JsonProperty(value = "appsecret")
    private String appSecret;
}
