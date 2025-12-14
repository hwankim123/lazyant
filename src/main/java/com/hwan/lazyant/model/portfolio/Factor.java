package com.hwan.lazyant.model.portfolio;

import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Arrays;

/**
 * 투자 관점
 */
@Getter
public enum Factor {
    UNKNOWN((short) 0),
    INDEX((short) 1),
    DIVIDEND((short) 2),
    VALUE((short) 3),
    GROWTH((short) 4),
    THEME((short) 5);

    private final short dbValue;

    Factor(short dbValue) {
        this.dbValue = dbValue;
    }

    public static Factor fromDbValue(@Nonnull Short dbValue) {
        return Arrays.stream(values())
                .filter(factor -> dbValue == factor.getDbValue())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("cannot get Factor of dbValue: %d".formatted(dbValue)));
    }
}
