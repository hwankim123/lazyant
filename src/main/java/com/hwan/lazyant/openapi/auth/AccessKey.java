package com.hwan.lazyant.openapi.auth;

import java.time.LocalDateTime;

public record AccessKey(String key, LocalDateTime expireAt) {

    public static AccessKey from(String accessToken, LocalDateTime expireAt) {
        return new AccessKey(accessToken, expireAt);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }
}
