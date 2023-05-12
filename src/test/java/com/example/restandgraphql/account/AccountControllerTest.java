package com.example.restandgraphql.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    private static final String PRINCIPAL_NAME = "test-user";

    private final Principal principal = () -> PRINCIPAL_NAME;
    private AccountService accountService;
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountServiceImpl.class);
        accountController = new AccountController(accountService, new ModelMapper());
    }

    @Test
    void getAccount() {
        var account = new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO);
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.of(account));
        var response = accountController.getAccount(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(account.getBalance(), response.getBody().getBalance());
        assertEquals(PRINCIPAL_NAME, response.getBody().getName());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
    }


    @Test
    void doNotGetAccountWhenItDoesNotExist() {
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.empty());
        var response = accountController.getAccount(principal);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
    }

    @Test
    void createAccount() {
        when(accountService.createAccount(PRINCIPAL_NAME)).thenReturn(new Account(null, PRINCIPAL_NAME, BigDecimal.ZERO));
        var response = accountController.createAccount(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BigDecimal.ZERO, response.getBody().getBalance());
        verify(accountService).createAccount(PRINCIPAL_NAME);
    }

    @Test
    void createAccountWhenAlreadyExists() {
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.of(new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO)));
        var response = accountController.createAccount(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BigDecimal.ZERO, response.getBody().getBalance());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
        verify(accountService, never()).createAccount(PRINCIPAL_NAME);
    }

    @Test
    void updateAccount() {
        var newAccountData = new AccountDto(PRINCIPAL_NAME, BigDecimal.TEN);
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.of(new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO)));
        var response = accountController.updateAccount(principal, newAccountData);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
        verify(accountService).updateAccount(any());
    }

    @Test
    void doNotUpdateAccountWhenPrincipalIsDifferent() {
        var newAccountData = new AccountDto("different-principal", BigDecimal.TEN);
        var response = accountController.updateAccount(principal, newAccountData);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(accountService, never()).updateAccount(any());
    }

    @Test
    void deleteAccount() {
        var id = 1L;
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.of(new Account(id, PRINCIPAL_NAME, BigDecimal.ZERO)));
        var response = accountController.deleteAccount(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
        verify(accountService).deleteAccountById(id);
    }

    @Test
    void doNotDeleteAccountWhenAccountDoesNotExist() {
        var id = 1L;
        when(accountService.getAccountByName(PRINCIPAL_NAME)).thenReturn(Optional.empty());
        var account = accountController.deleteAccount(principal);
        assertEquals(HttpStatus.BAD_REQUEST, account.getStatusCode());
        verify(accountService).getAccountByName(PRINCIPAL_NAME);
        verify(accountService, never()).deleteAccountById(id);
    }
}