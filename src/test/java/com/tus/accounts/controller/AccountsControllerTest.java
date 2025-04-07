package com.tus.accounts.controller;

import com.tus.accounts.dto.AccountsDto;
import com.tus.accounts.entity.Accounts;
import com.tus.accounts.service.AccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountsController.class)
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean
    private AccountsService accountsService;

    private AccountsDto accountsDto;

    @BeforeEach
    void setUp() {
        accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(12345L);
        accountsDto.setAccountType("Savings");
        accountsDto.setAccountHolderName("John Doe");
        accountsDto.setBranchAddress("123 Main St");
        accountsDto.setBalance(BigDecimal.valueOf(1000));
        accountsDto.setStatus("Active");
    }

    @Test
    void testCreateAccount() throws Exception {
        Accounts account = new Accounts();
        account.setAccountNumber(12345L);
        when(accountsService.createAccount(any(Accounts.class))).thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"accountNumber\": 12345, \"accountType\": \"Savings\", \"accountHolderName\": \"John Doe\", \"branchAddress\": \"123 Main St\", \"balance\": 1000, \"status\": \"Active\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(12345));
    }

    @Test
    void testCreateAccountValidationError() throws Exception {
        // Missing account number (should trigger validation error)
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"accountType\": \"Savings\", \"accountHolderName\": \"John Doe\", \"branchAddress\": \"123 Main St\", \"balance\": 1000, \"status\": \"Active\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAccountById() throws Exception {
        Accounts account = new Accounts();
        account.setId(1L);
        account.setAccountNumber(12345L);
        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(12345L));
    }

    @Test
    void testGetAccountByIdNotFound() throws Exception {
        when(accountsService.getAccountById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllAccounts() throws Exception {
        Accounts account = new Accounts();
        account.setAccountNumber(12345L);
        when(accountsService.getAllAccounts()).thenReturn(List.of(account));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value(12345L));
    }

    @Test
    void testGetAllAccountsEmptyList() throws Exception {
        // Mock empty list response
        when(accountsService.getAllAccounts()).thenReturn(List.of());

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]")); // Expect empty list
    }

    @Test
    void testUpdateAccount() throws Exception {
        Accounts existingAccount = new Accounts();
        existingAccount.setId(1L);
        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(existingAccount));
        when(accountsService.updateAccount(eq(1L), any(Accounts.class))).thenReturn(existingAccount);

        mockMvc.perform(put("/api/accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"accountNumber\": 12345, \"accountType\": \"Savings\", \"accountHolderName\": \"John Doe\", \"branchAddress\": \"123 Main St\", \"balance\": 1000, \"status\": \"Active\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAccountValidationError() throws Exception {
        // Invalid account data, missing required fields
        mockMvc.perform(put("/api/accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"accountType\": \"Savings\", \"accountHolderName\": \"John Doe\", \"branchAddress\": \"123 Main St\", \"balance\": 1000, \"status\": \"Active\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAccountNotFound() throws Exception {
        when(accountsService.getAccountById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"accountNumber\": 12345, \"accountType\": \"Savings\", \"accountHolderName\": \"John Doe\", \"branchAddress\": \"123 Main St\", \"balance\": 1000, \"status\": \"Active\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAccount() throws Exception {
        Accounts account = new Accounts();
        when(accountsService.getAccountById(1L)).thenReturn(Optional.of(account));
        doNothing().when(accountsService).deleteAccount(1L);

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAccountNotFound() throws Exception {
        when(accountsService.getAccountById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAccountValidationError() throws Exception {
        // Trying to delete an invalid account
        mockMvc.perform(delete("/api/accounts/9999"))
                .andExpect(status().isNotFound());
    }
}
