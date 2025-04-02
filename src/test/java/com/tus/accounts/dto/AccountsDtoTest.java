package com.tus.accounts.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AccountsDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Setup the validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAccountsDto() {
        // Creating a valid AccountsDto
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there are no violations (valid object)
        assertTrue(violations.isEmpty(), "There should be no validation errors.");
    }

    @Test
    public void testInvalidAccountsDto_AccountNumberNull() {
        // Creating an invalid AccountsDto with a null account number
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(null);  // Invalid: account number cannot be null
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the accountNumber
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("accountNumber")));
    }

    @Test
    public void testInvalidAccountsDto_AccountTypeEmpty() {
        // Creating an invalid AccountsDto with an empty account type
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("");  // Invalid: account type cannot be empty
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the accountType
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("accountType")));
    }

    @Test
    public void testInvalidAccountsDto_BranchAddressEmpty() {
        // Creating an invalid AccountsDto with an empty branch address
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("");  // Invalid: branch address cannot be empty
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the branchAddress
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("branchAddress")));
    }

    @Test
    public void testInvalidAccountsDto_AccountHolderNameEmpty() {
        // Creating an invalid AccountsDto with an empty account holder name
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("");  // Invalid: account holder name cannot be empty
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the accountHolderName
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("accountHolderName")));
    }

    @Test
    public void testInvalidAccountsDto_StatusEmpty() {
        // Creating an invalid AccountsDto with an empty status
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(new BigDecimal("1000.00"));
        accountsDto.setStatus("");  // Invalid: status cannot be empty

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the status
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")));
    }

    @Test
    public void testInvalidAccountsDto_BalanceNull() {
        // Creating an invalid AccountsDto with a null balance
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Main Branch");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBalance(null);  // Invalid: balance cannot be null
        accountsDto.setStatus("Active");

        // Validate the object
        Set<ConstraintViolation<AccountsDto>> violations = validator.validate(accountsDto);

        // Assert that there is 1 violation and it's for the balance
        assertFalse(violations.isEmpty(), "There should be validation errors.");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("balance")));
    }
}
