package com.monese.test.controllers;

import com.monese.test.dtos.TransactionRequest;
import com.monese.test.dtos.TransactionResponse;
import com.monese.test.services.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    BankTransactionService bankTransactionService;

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest){
        return bankTransactionService.sendMoney(transactionRequest);
    }

}
