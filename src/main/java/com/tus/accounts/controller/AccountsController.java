package com.tus.accounts.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.tus.accounts.dto.AccountsDto;
import com.tus.accounts.entity.Accounts;
import com.tus.accounts.service.AccountsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping
    public Accounts createAccount(@Valid @RequestBody AccountsDto accountsDto) {
    	Accounts accounts = mapToEntity(accountsDto);
        return accountsService.createAccount(accounts);
    }

    @GetMapping("/{id}")
    public Accounts getAccountById(@PathVariable Long id) {
    	Optional<Accounts> accounts = accountsService.getAccountById(id);
    	return accounts.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found"));
    }
      
    @GetMapping
    public List<Accounts> getAllAccounts() {
        return accountsService.getAllAccounts();
    }
    
    @PutMapping("/{id}")
    public Accounts updateAccount(@PathVariable Long id,@Valid @RequestBody AccountsDto accountsDto) {
    	Optional<Accounts> existingAccount = accountsService.getAccountById(id);
        if (existingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        }
        Accounts accounts = mapToEntity(accountsDto);
        return accountsService.updateAccount(id, accounts);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
    	Optional<Accounts> existingAccount = accountsService.getAccountById(id);
        if (existingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        }
        accountsService.deleteAccount(id);
    }
    
    private Accounts mapToEntity(AccountsDto accountsDto) {
    	Accounts account = new Accounts();
    	account.setAccountNumber(accountsDto.getAccountNumber());
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());
        account.setAccountHolderName(accountsDto.getAccountHolderName());
        account.setBalance(accountsDto.getBalance());
        account.setStatus(accountsDto.getStatus());
        return account;
    }
}
