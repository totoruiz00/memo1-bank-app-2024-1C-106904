package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        Transaction transaction = new Transaction(
            TransactionType.WITHDRAW,
            sum,
            account
        );
        transactionService.createTransaction(transaction);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        Account account = accountRepository.findAccountByCbu(cbu);

        if (sum >= 2000) {
            double extra = sum * 0.10;
            if (extra > 500) {
                extra = 500;
            }
            sum += extra;
        }
        
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);

        Transaction transaction = new Transaction(
            TransactionType.DEPOSIT,
            sum,
            account
        );
        transactionService.createTransaction(transaction);

        return account;
    }
}