package com.hwan.lazyant.openapi.auth;

import com.hwan.lazyant.openapi.common.OpenApiProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OpenApiAccessKeyManager {
    private final Map<OpenApiProvider, AccessKey> accessKeys = new HashMap<>();

    public AccessKey save(OpenApiProvider provider, AccessKey accessKey) {
        this.accessKeys.put(provider, accessKey);
        return this.accessKeys.get(provider);
    }

    public Optional<AccessKey> get(OpenApiProvider provider) {
        return Optional.ofNullable(this.accessKeys.get(provider));
    }

    public void expire(OpenApiProvider provider) {
        this.accessKeys.remove(provider);
    }
}
