package com.monese.test.utils;

import com.monese.test.dtos.AccountDto;
import com.monese.test.model.Account;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Generator {
    public static Account generateRandomAccount() {
        Account account = new Account();
        account.setAccountNumber(RandomStringUtils.randomAlphanumeric(15));
        account.setFirstName(RandomStringUtils.randomAlphabetic(5));
        account.setLastName(RandomStringUtils.randomAlphabetic(5));
        account.setBalance(new BigDecimal(1000).setScale(2, RoundingMode.HALF_UP));
        account.setEmail(RandomStringUtils.randomAlphabetic(20));
        account.setTransactions(new ArrayList<>());
        return account;
    }

    public static AccountDto generateRandomAccountDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(RandomStringUtils.randomAlphanumeric(15));
        accountDto.setFirstName(RandomStringUtils.randomAlphabetic(5));
        accountDto.setLastName(RandomStringUtils.randomAlphabetic(5));
        accountDto.setBalance(new BigDecimal(1000.00).setScale(2, RoundingMode.HALF_UP));
        accountDto.setEmail(RandomStringUtils.randomAlphabetic(20));
        accountDto.setTransactions(new ArrayList<>());

        return accountDto;
    }
}
