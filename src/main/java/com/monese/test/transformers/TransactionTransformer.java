package com.monese.test.transformers;

import com.monese.test.dtos.TransactionDto;
import com.monese.test.model.Transaction;

public class TransactionTransformer {

    public static TransactionDto toTransactionDto(Transaction transaction) {

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setTransactionTime(transaction.getTransactionDate());
        return transactionDto;
    }

}
