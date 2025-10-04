package com.hwan.lazyant.mapper.account;

import com.hwan.lazyant.controller.account.dto.AccountInsertRequest;
import com.hwan.lazyant.model.account.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountInsertRequest request) {
        return new Account(request.getName(), request.getName(), request.getBank());
    }
}
