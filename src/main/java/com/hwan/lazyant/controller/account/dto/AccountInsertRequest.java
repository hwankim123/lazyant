package com.hwan.lazyant.controller.account.dto;

import com.hwan.lazyant.model.account.type.Bank;
import lombok.Getter;

@Getter
public class AccountInsertRequest {
    private String name;
    private String nickname;
    private Bank bank;
}
