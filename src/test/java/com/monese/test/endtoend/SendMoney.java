package com.monese.test.endtoend;


import com.monese.test.dtos.AccountDto;
import com.monese.test.dtos.TransactionDto;
import com.monese.test.dtos.TransactionRequest;
import com.monese.test.dtos.TransactionResponse;
import com.monese.test.enums.TransactionType;
import com.monese.test.utils.Generator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendMoney {

    @Autowired
    private TestRestTemplate restTemplate;

    private AccountDto sender;
    private AccountDto receiver;

    @Before
    public void init(){
        this.sender = restTemplate.postForEntity("/accounts", Generator.generateRandomAccountDto(), AccountDto.class).getBody();
        this.receiver = restTemplate.postForEntity("/accounts", Generator.generateRandomAccountDto(), AccountDto.class).getBody();
    }

    @Test
    public void testSendMoneySuccessfully(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        //make transaction for and check if sucess
        ResponseEntity<TransactionResponse> responseEntity =
                restTemplate.postForEntity("/transactions", transactionRequest, TransactionResponse.class);
        TransactionResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Money send successful!", response.getMessage());

        //get sender account and check if transaction change accounts properties
        AccountDto senderAfterTransaction = restTemplate.getForEntity("/accounts/"+sender.getAccountNumber(), AccountDto.class).getBody();

        assertEquals(new BigDecimal(800).setScale(2, RoundingMode.HALF_UP), senderAfterTransaction.getBalance());
        assertEquals(1, senderAfterTransaction.getTransactions().size());
        TransactionDto senderTransactionDto = senderAfterTransaction.getTransactions().get(0);
        assertEquals(TransactionType.SEND, senderTransactionDto.getTransactionType());
        assertEquals(new BigDecimal(200).setScale(2, RoundingMode.HALF_UP), senderTransactionDto.getAmount());

        //get receiver account and check if transaction change accounts properties
        AccountDto receiverAfterTransaction = restTemplate.getForEntity("/accounts/"+receiver.getAccountNumber(), AccountDto.class).getBody();

        assertEquals(new BigDecimal(1200).setScale(2, RoundingMode.HALF_UP), receiverAfterTransaction.getBalance());
        assertEquals(1, receiverAfterTransaction.getTransactions().size());
        TransactionDto receiverTransactionDto = receiverAfterTransaction.getTransactions().get(0);
        assertEquals(TransactionType.RECEIVE, receiverTransactionDto.getTransactionType());
        assertEquals(new BigDecimal(200).setScale(2, RoundingMode.HALF_UP), receiverTransactionDto.getAmount());

    }

    @Test
    public void senderDoesNotExist(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderAccountNumber("NotExist");
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        ResponseEntity<TransactionResponse> responseEntity =
                restTemplate.postForEntity("/transactions", transactionRequest, TransactionResponse.class);
        TransactionResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Sender Not Found", response.getMessage());
        assertNothingChangedForAccounts();

    }

    @Test
    public void receiverDoesNotExist(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setReceiverAccountNumber("NotExist");
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(200));

        ResponseEntity<TransactionResponse> responseEntity =
                restTemplate.postForEntity("/transactions", transactionRequest, TransactionResponse.class);
        TransactionResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Receiver Not Found", response.getMessage());
        assertNothingChangedForAccounts();
    }

    @Test
    public void senderBalanceNotEnough(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setReceiverAccountNumber(receiver.getAccountNumber());
        transactionRequest.setSenderAccountNumber(sender.getAccountNumber());
        transactionRequest.setAmount(new BigDecimal(2000));

        ResponseEntity<TransactionResponse> responseEntity =
                restTemplate.postForEntity("/transactions", transactionRequest, TransactionResponse.class);
        TransactionResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Sender's balance not enough", response.getMessage());
        assertNothingChangedForAccounts();
    }

    private void assertNothingChangedForAccounts() {
        //get sender account and check if transaction change accounts properties
        AccountDto senderAfterTransaction = restTemplate.getForEntity("/accounts/"+sender.getAccountNumber(), AccountDto.class).getBody();

        assertEquals(new BigDecimal(1000).setScale(2, RoundingMode.HALF_UP), senderAfterTransaction.getBalance());
        assertEquals(0, senderAfterTransaction.getTransactions().size());

        //get receiver account and check if transaction change accounts properties
        AccountDto receiverAfterTransaction = restTemplate.getForEntity("/accounts/"+receiver.getAccountNumber(), AccountDto.class).getBody();

        assertEquals(new BigDecimal(1000).setScale(2, RoundingMode.HALF_UP), receiverAfterTransaction.getBalance());
        assertEquals(0, receiverAfterTransaction.getTransactions().size());
    }
}
