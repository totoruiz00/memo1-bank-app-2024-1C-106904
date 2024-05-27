package com.aninfo.service;
import com.aninfo.repository.AccountRepository;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsByAccount(Long cbu) {
        return transactionRepository.findByAccountCbu(cbu);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void deleteTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
    
        if (transaction.getType() == TransactionType.DEPOSIT) {

            Account account = transaction.getAccount();
            account.setBalance(account.getBalance() - transaction.getAmount());
            accountRepository.save(account);
        } else if (transaction.getType() == TransactionType.WITHDRAW) {

            Account account = transaction.getAccount();
            account.setBalance(account.getBalance() + transaction.getAmount());
            accountRepository.save(account);
        }
        transactionRepository.deleteById(id);
    }

}