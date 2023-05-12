package com.example.restandgraphql.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static graphql.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    private static final String PRINCIPAL_NAME = "test-user";
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void getAccountById() {
        var id = 1L;
        var accountDatabase = new Account(id, PRINCIPAL_NAME, BigDecimal.ZERO);
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountDatabase));
        var response = accountService.getAccountById(id);
        assertTrue(response.isPresent());
        assertEquals(accountDatabase, response.get());
        verify(accountRepository).findById(id);
    }

    @Test
    void getAccountByName() {
        var accountDatabase = new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO);
        when(accountRepository.findByName(PRINCIPAL_NAME)).thenReturn(Optional.of(accountDatabase));
        var response = accountService.getAccountByName(PRINCIPAL_NAME);
        assertTrue(response.isPresent());
        assertEquals(accountDatabase, response.get());
        verify(accountRepository).findByName(PRINCIPAL_NAME);
    }

    @Test
    void createAccount() {
        var accountToCreate = new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO);
        when(accountRepository.save(any())).thenReturn(accountToCreate);
        var response = accountService.createAccount(PRINCIPAL_NAME);
        assertEquals(accountToCreate, response);
        verify(accountRepository).save(any());
    }

    @Test
    void updateAccount() {
        var accountToUpdate = new Account(1L, PRINCIPAL_NAME, BigDecimal.ZERO);
        when(accountRepository.save(accountToUpdate)).thenReturn(accountToUpdate);
        accountService.updateAccount(accountToUpdate);
        verify(accountRepository).save(accountToUpdate);
    }

    @Test
    void doNotUpdateAccountWhenIdIsNull() {
        var accountToUpdate = new Account(null, PRINCIPAL_NAME, BigDecimal.ZERO);
        assertThrows(RequiredFieldNotFound.class, () -> accountService.updateAccount(accountToUpdate));
        verify(accountRepository, never()).save(accountToUpdate);
    }

    @Test
    void deleteAccountById() {
        var id = 1L;
        accountService.deleteAccountById(id);
        verify(accountRepository).deleteById(id);
    }

    @Test
    void getAccounts() {
        var account = new Account(1L, "test", BigDecimal.ZERO);
        var list = List.of(account);
        when(accountRepository.findAll()).thenReturn(list);
        var response = accountService.getAccounts();
        assertEquals(1, response.size());
        assertEquals(account, response.get(0));
        verify(accountRepository).findAll();
    }
}