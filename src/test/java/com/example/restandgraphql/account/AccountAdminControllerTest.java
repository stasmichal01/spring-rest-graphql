package com.example.restandgraphql.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountAdminControllerTest {
    private AccountService accountService;
    private AccountAdminController accountController;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountServiceImpl.class);
        accountController = new AccountAdminController(accountService, new ModelMapper());
    }

    @Test
    void getAccounts() {
        var account = new Account(1L, "test", BigDecimal.ZERO);
        var list = List.of(account);
        when(accountService.getAccounts()).thenReturn(list);
        var response = accountController.getAccounts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, list.size());
        assertEquals(account, list.get(0));
        verify(accountService).getAccounts();
    }

    @Test
    void getAccountsWhenItIsEmpty() {
        when(accountService.getAccounts()).thenReturn(emptyList());
        var response = accountController.getAccounts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList(), response.getBody());
        verify(accountService).getAccounts();
    }
}