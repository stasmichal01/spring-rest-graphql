package com.example.restandgraphql.account;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/account")
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<AccountDto> getAccount(Principal principal) {
        var account = accountService.getAccountByName(principal.getName());
        if (account.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(account.get(), AccountDto.class));
    }
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(Principal principal) {
        var username = principal.getName();
        var account = accountService.getAccountByName(username)
                .orElseGet(() -> accountService.createAccount(username));
        return ResponseEntity.ok(modelMapper.map(account, AccountDto.class));
    }

    @PutMapping
    public ResponseEntity<Void> updateAccount(Principal principal, @RequestBody AccountDto body) {
        var username = principal.getName();
        if (!username.equals(body.getName())) {
            return ResponseEntity.badRequest().build();
        }
        var accountId = accountService.getAccountByName(username).map(Account::getId);
        if (accountId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var updatedAccount = modelMapper.map(body, Account.class);
        updatedAccount.setId(accountId.get());
        accountService.updateAccount(updatedAccount);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(Principal principal) {
        var accountId = accountService.getAccountByName(principal.getName()).map(Account::getId);
        if (accountId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        accountService.deleteAccountById(accountId.get());
        return ResponseEntity.ok().build();
    }
}