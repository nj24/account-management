package com.project.account.management.service;

import com.project.account.management.domain.model.Account;
import com.project.account.management.exception.InsufficientBalanceException;
import com.project.account.management.exception.ResourceNotFoundException;
import com.project.account.management.repository.AccountRepository;
import com.project.account.management.api.model.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;


@Service
public class AccountService {

    public static final BigDecimal MIN_ACCOUNT_BALANCE = BigDecimal.ZERO;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferBalance(final UUID sourceAccountRef,
                                final UUID destinationAccountRef,
                                final BigDecimal amountToTransfer) {
        //withdraw from source
        updateAccountBalance(sourceAccountRef, amountToTransfer.multiply(BigDecimal.valueOf(-1)));
        //deposit to Destination
        updateAccountBalance(destinationAccountRef, amountToTransfer);
    }

    public void updateAccountBalance(final UUID accountRef, final BigDecimal amount) {
        Account account = accountRepository.findOptionalByRef(accountRef)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "ref", accountRef));

        BigDecimal updatedBalance = account.getBalance().add(amount);
        if (updatedBalance.compareTo(MIN_ACCOUNT_BALANCE) < 0) {
            throw new InsufficientBalanceException("Account", "ref", accountRef);
        }
        account.setBalance(updatedBalance);
        accountRepository.save(account);
    }

    public Account getAccount(final UUID accountRef) {
        return accountRepository.findOptionalByRef(accountRef)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Ref", accountRef));
    }

    public void deleteAccount(final UUID accountRef) {
        accountRepository.delete(getAccount(accountRef));
    }

    public Account createNewAccount(final CreateAccountRequest request) {
        new Account();
        Account account = Account.builder()
                .ref(UUID.randomUUID())
                .balance(request.initialBalance())
                .name(request.accountName())
                .build();

        accountRepository.save(account);
        return account;
    }
}
