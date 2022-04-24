package com.project.account.management.controller;

import com.project.account.management.api.model.CreateAccountResponse;
import com.project.account.management.domain.model.Account;
import com.project.account.management.repository.AccountRepository;
import com.project.account.management.api.model.CreateAccountRequest;
import com.project.account.management.api.model.TransferBalanceRequest;
import com.project.account.management.api.model.UpdateBalanceRequest;
import com.project.account.management.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountService accountService;

  @GetMapping("/accounts")
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @GetMapping("/accounts/{ref}")
  public Account getAccountById(@PathVariable(value = "ref") UUID accountRef) {
    return accountService.getAccount(accountRef);
  }

  @PostMapping("/accounts")
  @Transactional
  public ResponseEntity<CreateAccountResponse> createAccount(
      @Valid @RequestBody CreateAccountRequest request) {
    Account newAccount = accountService.createNewAccount(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new CreateAccountResponse(newAccount.getRef(),
            newAccount.getName(),
            newAccount.getBalance()));
  }

  @PatchMapping("/accounts/{ref}")
  @Transactional
  public ResponseEntity<Void> updateAccountBalance(@PathVariable(value = "ref") UUID accountRef,
      @Valid @RequestBody UpdateBalanceRequest updateBalanceRequest) {
    accountService.updateAccountBalance(accountRef, updateBalanceRequest.balance());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/accounts/transfer")
  @Transactional
  public ResponseEntity<Void> transferAmount(
      @Valid @RequestBody TransferBalanceRequest transferBalanceRequest) {
    accountService.transferBalance(transferBalanceRequest.sourceAccountRef(),
        transferBalanceRequest.destinationAccountRef(),
        transferBalanceRequest.amountToTransfer());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/accounts/{ref}")
  @Transactional
  public ResponseEntity<?> deleteAccount(@PathVariable(value = "ref") UUID accountRef) {
    accountService.deleteAccount(accountRef);
    return ResponseEntity.ok().build();
  }
}

