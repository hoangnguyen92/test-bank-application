package com.monese.test.controllers;

import com.monese.test.dtos.AccountDto;
import com.monese.test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AccountDto createAccount(AccountDto accountDto){
        return accountService.createAccount(accountDto);
    }
}
