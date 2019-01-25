package com.monese.test.dtos;

import com.monese.test.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionDto {
    private TransactionType transactionType;
    private BigDecimal amount;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
