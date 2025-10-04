package com.hwan.lazyant.repository.account;

import com.hwan.lazyant.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
