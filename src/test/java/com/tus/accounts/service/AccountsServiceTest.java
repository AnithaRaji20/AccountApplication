package com.tus.accounts.service;

import com.tus.accounts.entity.Accounts;
import com.tus.accounts.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    private AccountsService accountsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountsService = new AccountsService(accountsRepository);
    }

    @Test
    void testCreateAccount() {
        Accounts account = new Accounts();
        account.setAccountNumber(12345L);
        account.setAccountType("Savings");
        account.setBalance(BigDecimal.valueOf(1000));
        when(accountsRepository.save(any(Accounts.class))).thenReturn(account);

        Accounts createdAccount = accountsService.createAccount(account);

        assertNotNull(createdAccount);
        assertEquals(12345L, createdAccount.getAccountNumber());
    }

    @Test
    void testGetAccountById() {
        Accounts account = new Accounts();
        account.setId(1L);
        account.setAccountNumber(12345L);
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Accounts> foundAccount = accountsService.getAccountById(1L);

        assertTrue(foundAccount.isPresent());
        assertEquals(12345L, foundAccount.get().getAccountNumber());
    }

    @Test
    void testGetAllAccounts() {
        Accounts account = new Accounts();
        when(accountsRepository.findAll()).thenReturn(List.of(account));

        var accounts = accountsService.getAllAccounts();
        assertFalse(accounts.isEmpty());
    }

    @Test
    void testUpdateAccount() {
        Accounts account = new Accounts();
        account.setId(1L);
        when(accountsRepository.save(account)).thenReturn(account);

        Accounts updated = accountsService.updateAccount(1L, account);
        assertEquals(1L, updated.getId());
    }

    @Test
    void testDeleteAccount() {
        doNothing().when(accountsRepository).deleteById(1L);
        accountsService.deleteAccount(1L);
        verify(accountsRepository, times(1)).deleteById(1L);
    }
}
