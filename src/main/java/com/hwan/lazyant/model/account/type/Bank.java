package com.hwan.lazyant.model.account.type;

public enum Bank {
    TOSS("토스증권"),
    KOREA_INVESTMENT("한국투자증권"),
    SAMSUNG("삼성증권"),
    KB("국민은행");

    final String name;

    Bank(String name) {
        this.name = name;
    }
}
