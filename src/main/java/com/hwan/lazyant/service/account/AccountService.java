package com.hwan.lazyant.service.account;

import com.hwan.lazyant.controller.account.dto.AccountInsertRequest;
import com.hwan.lazyant.mapper.account.AccountMapper;
import com.hwan.lazyant.model.account.Account;
import com.hwan.lazyant.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void insert(AccountInsertRequest request) {
        Account newAccount = AccountMapper.mapToAccount(request);
        accountRepository.save(newAccount);
    }
}
