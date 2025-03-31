package com.tus.accounts.service;

import com.tus.accounts.entity.Accounts;
import com.tus.accounts.repository.AccountsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {

    private AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Accounts createAccount(Accounts account) {
        return accountsRepository.save(account);
    }

    public Optional<Accounts> getAccountById(Long id) {
        return accountsRepository.findById(id);
    }

    public List<Accounts> getAllAccounts() {
        return accountsRepository.findAll();
    }

    public Accounts updateAccount(Long id, Accounts accounts) {
    	accounts.setId(id);
        return accountsRepository.save(accounts);
    }

    public void deleteAccount(Long id) {
        accountsRepository.deleteById(id);
    }
}