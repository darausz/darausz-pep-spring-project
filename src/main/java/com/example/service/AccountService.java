package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
  private AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account register(Account account) throws Exception {
    if (account.getUsername().isBlank() || account.getPassword().length() < 5) {
      throw new Exception("400");
    }
    Account existingAccount = accountRepository.findByUsername(account.getUsername());
    if (existingAccount != null) {
      throw new Exception("409");
    }
    accountRepository.save(account);
    return accountRepository.findByUsername(account.getUsername());
  }

  public Account login(Account account) throws Exception{
    Account existingAccount = accountRepository.findByUsername(account.getUsername());
    if (existingAccount == null) {
      throw new Exception("401");
    }
    if (!existingAccount.getUsername().equals(account.getUsername()) || !existingAccount.getPassword().equals(account.getPassword())) {
      throw new Exception("401");
    }
    return existingAccount;
  }

  public Boolean accountExists(Integer accountId) {
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isPresent()) {
      return true;
    }
    return false;
  }
}
