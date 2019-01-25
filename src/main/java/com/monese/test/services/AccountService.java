package com.monese.test.services;

import com.monese.test.dtos.AccountDto;
import com.monese.test.model.Account;
import com.monese.test.repositories.AccountRepository;
import com.monese.test.transformers.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountDto> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(AccountTransformer::toAccountDTO)
                .collect(Collectors.toList());

    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountRepository.save(AccountTransformer.toAccount(accountDto));

        return AccountTransformer.toAccountDTO(account);
    }
}
