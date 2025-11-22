package com.hwan.lazyant.openapi.provider.koreainvestment.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GetAccessKeyResponse {

    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("token_type")
    private String tokenType;

    @JsonAlias("expires_in")
    private int expiresIn;

    @JsonAlias("access_token_token_expired")
    private String accessTokenTokenExpired;

    public LocalDateTime expireAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(accessTokenTokenExpired, formatter);
    }
}