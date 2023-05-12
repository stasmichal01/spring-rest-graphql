package com.example.restandgraphql.account;

import java.util.List;
import java.util.Optional;

interface AccountService {
    Optional<Account> getAccountById(Long accountId);

    Optional<Account> getAccountByName(String name);

    List<Account> getAccounts();

    Account createAccount(String name);

    void updateAccount(Account account);

    void deleteAccountById(Long accountId);
}