package com.tus.accounts.service;

import com.tus.accounts.entity.Accounts;
import com.tus.accounts.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsService accountsService;

    private Accounts account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Accounts();
        account.setId(1L);
        account.setAccountNumber(12345L);
        account.setAccountType("Savings");
        account.setBranchAddress("Main Branch");
        account.setAccountHolderName("John Doe");
        account.setBalance(new BigDecimal("1000.00"));
        account.setStatus("Active");
    }

    @Test
    public void testCreateAccount() {
        when(accountsRepository.save(any(Accounts.class))).thenReturn(account);

        Accounts result = accountsService.createAccount(account);
        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }
    
    @Test
    public void testGetAllAccounts() {
        // Arrange: Mock the repository to return a list of accounts
        when(accountsRepository.findAll()).thenReturn(Arrays.asList(account));

        // Act: Call the service method
        List<Accounts> accountsList = accountsService.getAllAccounts();

        // Assert: Verify that the repository method was called and check the returned data
        verify(accountsRepository, times(1)).findAll();  // Ensure findAll() was called once
        assertNotNull(accountsList);  // Ensure the result is not null
        assertEquals(account.getBranchAddress(), accountsList.get(0).getBranchAddress());
        assertEquals(account.getAccountHolderName(), accountsList.get(0).getAccountHolderName()); 
        assertEquals(account.getStatus(), accountsList.get(0).getStatus()); 
        assertEquals(account.getAccountNumber(), accountsList.get(0).getAccountNumber());  
    }
    
    

    @Test
    public void testGetAccountById() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Accounts> result = accountsService.getAccountById(1L);
        assertTrue(result.isPresent());
        assertEquals(account.getAccountNumber(), result.get().getAccountNumber());
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Accounts> result = accountsService.getAccountById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateAccount() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountsRepository.save(any(Accounts.class))).thenReturn(account);

        account.setBalance(new BigDecimal("1500.00"));
        Accounts result = accountsService.updateAccount(1L, account);
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
    }

    @Test
    public void testDeleteAccount() {
        doNothing().when(accountsRepository).deleteById(1L);

        accountsService.deleteAccount(1L);
        verify(accountsRepository, times(1)).deleteById(1L);
    }
}
