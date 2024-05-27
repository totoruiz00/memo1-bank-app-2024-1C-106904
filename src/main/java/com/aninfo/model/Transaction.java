package com.aninfo.model;

import javax.persistence.*;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "account_cbu")
    private Account account;

    public Transaction(){

    }

    public Transaction(TransactionType type, Double amount, Account account) {
        this.type = type;
        this.amount = amount;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}