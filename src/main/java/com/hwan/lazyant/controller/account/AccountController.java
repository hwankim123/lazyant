package com.hwan.lazyant.controller.account;

import com.hwan.lazyant.controller.account.dto.AccountInsertRequest;
import com.hwan.lazyant.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public void insert(@RequestBody AccountInsertRequest request) {
        accountService.insert(request);
    }
}
