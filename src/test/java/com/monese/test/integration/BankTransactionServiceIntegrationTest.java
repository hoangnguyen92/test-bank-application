package com.monese.test.integration;

import com.monese.test.dtos.AccountDto;
import com.monese.test.dtos.TransactionRequest;
import com.monese.test.dtos.TransactionResponse;
import com.monese.test.services.AccountService;
import com.monese.test.services.BankTransactionService;
import com.monese.test.utils.Generator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BankTransactionServiceIntegrationTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BankTransactionService bankTransactionService;

    private AccountDto sender;
    private AccountDto receiver;

    @Before
    public void init(){
        this.sender = accountService.createAccount(Generator.generateRandomAccountDto());
        this.receiver = accountService.createAccount(Generator.generateRandomAccountDto());
    }

    @Test
    public void sendMoneyTest(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        TransactionResponse response = bankTransactionService.sendMoney(transactionRequest);

        Assertions.assertEquals("Money send successful!", response.getMessage());
    }

    @Test
    public void senderDoesNotExist(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderAccountNumber("NotExist");
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()->bankTransactionService.sendMoney(transactionRequest));
        Assertions.assertEquals(HttpStatus.NOT_FOUND,ex.getStatus());
    }

    @Test
    public void receiverDoesNotExist(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setReceiverAccountNumber("NotExist");
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()->bankTransactionService.sendMoney(transactionRequest));
        Assertions.assertEquals(HttpStatus.NOT_FOUND,ex.getStatus());
    }

    @Test
    public void senderBalanceNotEnough(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(2000));

        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()->bankTransactionService.sendMoney(transactionRequest));
        Assertions.assertEquals(HttpStatus.FORBIDDEN,ex.getStatus());
    }
}
