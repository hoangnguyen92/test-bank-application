package com.monese.test.controllers;

import com.monese.test.dtos.AccountDto;
import com.monese.test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountDto> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody  AccountDto accountDto){
        return accountService.createAccount(accountDto);
    }

    @GetMapping (path = "{accountNumber}")
    public AccountDto getAccountByAccountNumber(@PathVariable String accountNumber){
        return accountService.getAccountByAccountNumber(accountNumber);
    }
}
