package com.tus.accounts.controller;

import com.tus.accounts.dto.AccountsDto;
import com.tus.accounts.entity.Accounts;
import com.tus.accounts.service.AccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountsControllerTest {

    @InjectMocks
    private AccountsController accountsController;

    @Mock
    private AccountsService accountsService;

    private AccountsDto accountsDto;

    @BeforeEach
    public void setUp() {
        accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");
    }

    @Test
    public void testCreateAccount() {
        Accounts account = new Accounts();
        account.setId(1L);
        account.setAccountNumber(accountsDto.getAccountNumber());
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());
        account.setAccountHolderName(accountsDto.getAccountHolderName());
        account.setBalance(accountsDto.getBalance());
        account.setStatus(accountsDto.getStatus());

        when(accountsService.createAccount(any(Accounts.class))).thenReturn(account);

        Accounts result = accountsController.createAccount(accountsDto);
        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    public void testGetAllAccounts() {
    	Accounts account1 = new Accounts();
        account1.setId(1L);
        account1.setAccountNumber(12345L);
        account1.setAccountType("Savings");
        account1.setBranchAddress("Main Branch");
        account1.setAccountHolderName("John Doe");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setStatus("Active");
        // Arrange: Mock the service to return a list of accounts
        when(accountsService.getAllAccounts()).thenReturn(Arrays.asList(account1));

        // Act: Call the controller method
        List<Accounts> result = accountsController.getAllAccounts();

        // Assert: Verify that the result is correct
        assertNotNull(result);  // Ensure the result is not null
        assertEquals(1, result.size());  // Ensure the list size is correct
        assertEquals("Main Branch", result.get(0).getBranchAddress()); 
        assertEquals("John Doe", result.get(0).getAccountHolderName());  
        assertEquals("Active", result.get(0).getStatus()); 
        
        // Verify that the service method was called once
        verify(accountsService, times(1)).getAllAccounts();
    }
    
    @Test
    public void testGetAccountById_Success() {
        Accounts account = new Accounts();
        account.setId(1L);
        account.setAccountNumber(accountsDto.getAccountNumber());
        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(account));

        Accounts result = accountsController.getAccountById(1L);
        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountsService.getAccountById(1L)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            accountsController.getAccountById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Account Not Found", thrown.getReason());
    }

    @Test
    public void testUpdateAccount_Success() {
        Accounts existingAccount = new Accounts();
        existingAccount.setId(1L);
        existingAccount.setAccountNumber(12345L);
        existingAccount.setAccountType("Savings");
        existingAccount.setBranchAddress("Main Branch");
        existingAccount.setAccountHolderName("John Doe");
        existingAccount.setBalance(new BigDecimal("1000.00"));
        existingAccount.setStatus("Active");

        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(existingAccount));

        Accounts updatedAccount = new Accounts();
        updatedAccount.setId(1L);
        updatedAccount.setAccountNumber(12345L);
        updatedAccount.setAccountType("Checking");
        updatedAccount.setBranchAddress("Branch 2");
        updatedAccount.setAccountHolderName("John Doe");
        updatedAccount.setBalance(new BigDecimal("1500.00"));
        updatedAccount.setStatus("Active");

        when(accountsService.updateAccount(anyLong(), any(Accounts.class))).thenReturn(updatedAccount);

        Accounts result = accountsController.updateAccount(1L, accountsDto);
        assertNotNull(result);
        assertEquals("Checking", result.getAccountType());
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
    }

    @Test
    public void testUpdateAccount_NotFound() {
        when(accountsService.getAccountById(1L)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            accountsController.updateAccount(1L, accountsDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Account Not Found", thrown.getReason());
    }

    @Test
    public void testDeleteAccount_Success() {
        Accounts existingAccount = new Accounts();
        existingAccount.setId(1L);

        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(existingAccount));
        doNothing().when(accountsService).deleteAccount(1L);

        accountsController.deleteAccount(1L);
        verify(accountsService, times(1)).deleteAccount(1L);
    }

    @Test
    public void testDeleteAccount_NotFound() {
        when(accountsService.getAccountById(1L)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            accountsController.deleteAccount(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Account Not Found", thrown.getReason());
    }
}
