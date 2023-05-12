package com.example.restandgraphql.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
    public Optional<Account> getAccountByName(String name) {
        return accountRepository.findByName(name);
    }
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
    public Account createAccount(String name) {
        return accountRepository.save(new Account(null, name, BigDecimal.ZERO));
    }
    public void updateAccount(Account account) {
        if (account.getId() == null) {
            throw new RequiredFieldNotFound("Id cannot be null");
        }
        accountRepository.save(account);
    }
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}