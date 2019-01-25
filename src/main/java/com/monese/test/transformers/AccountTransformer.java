package com.monese.test.transformers;

import com.monese.test.dtos.AccountDto;
import com.monese.test.dtos.TransactionDto;
import com.monese.test.model.Account;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class AccountTransformer {

    public static AccountDto toAccountDTO(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance().setScale(2, RoundingMode.HALF_UP));
        accountDto.setEmail(account.getEmail());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());

        List<TransactionDto> transactionDtos = account.getTransactions().stream()
                .map(TransactionTransformer::toTransactionDto)
                .collect(Collectors.toList());
        accountDto.setTransactions(transactionDtos);

        return accountDto;
    }

    public static Account toAccount(AccountDto accountDto) {
        Account account = new Account();

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setEmail(accountDto.getEmail());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());

        return account;
    }
}
