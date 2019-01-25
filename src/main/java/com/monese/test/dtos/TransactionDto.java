package com.monese.test.dtos;

import com.monese.test.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDto {
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionTime;

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

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionDto that = (TransactionDto) o;
        return Objects.equals(transactionType, that.transactionType)
                && Objects.equals(amount, that.amount)
                && Objects.equals(transactionTime, that.transactionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, amount, transactionTime);
    }
}
