package com.example.restandgraphql.account;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/account-admin")
public class AccountAdminController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        var accounts = accountService.getAccounts().stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
        return ResponseEntity.ok(accounts);
    }
}