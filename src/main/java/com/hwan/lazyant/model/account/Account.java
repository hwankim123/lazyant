package com.hwan.lazyant.model.account;

import com.hwan.lazyant.model.account.type.Bank;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "la_accounts")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    @Column
    private String name;

    @Column
    private String nickname;

    @Nonnull
    private Bank bank;

    public Account(@Nonnull String name, @Nullable String nickname, @Nonnull Bank bank) {
        this.name = name;
        this.nickname = nickname;
        this.bank = bank;
    }
}
