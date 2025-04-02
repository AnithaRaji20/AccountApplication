package com.tus.accounts.repository;

import com.tus.accounts.entity.Accounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // This sets up an in-memory H2 database to test JPA repositories
public class AccountsRepositoryTest {

    @Autowired
    private AccountsRepository accountsRepository;

    private Accounts account;

    @BeforeEach
    public void setUp() {
        account = new Accounts();
        account.setAccountNumber(12345L);
        account.setAccountType("Savings");
        account.setBranchAddress("Main Branch");
        account.setAccountHolderName("John Doe");
        account.setBalance(new BigDecimal("1000.00"));
        account.setStatus("Active");
    }

    @Test
    public void testSave() {
        // Save the account and check that it's saved successfully
        Accounts savedAccount = accountsRepository.save(account);
        assertNotNull(savedAccount);
        assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
    }

    @Test
    public void testFindById() {
        // Save the account first
        accountsRepository.save(account);

        // Fetch the account by ID and verify its existence
        Optional<Accounts> result = accountsRepository.findById(account.getId());
        assertTrue(result.isPresent());
        assertEquals(account.getAccountNumber(), result.get().getAccountNumber());
    }

    @Test
    public void testFindById_NotFound() {
        // Try fetching an account that doesn't exist
        Optional<Accounts> result = accountsRepository.findById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteById() {
        // Save the account first
        Accounts savedAccount = accountsRepository.save(account);

        // Delete the account
        accountsRepository.deleteById(savedAccount.getId());

        // Try fetching the account after deletion
        Optional<Accounts> result = accountsRepository.findById(savedAccount.getId());
        assertFalse(result.isPresent());
    }
}
