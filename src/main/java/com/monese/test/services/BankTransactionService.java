package com.monese.test.services;

import com.monese.test.dtos.TransactionRequest;
import com.monese.test.dtos.TransactionResponse;
import com.monese.test.enums.TransactionType;
import com.monese.test.model.Account;
import com.monese.test.model.Transaction;
import com.monese.test.repositories.AccountRepository;
import com.monese.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BankTransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse sendMoney(TransactionRequest transactionRequest){
        if(transactionRequest.getReceiverAccountNumber().equals(transactionRequest.getSenderAccountNumber()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Sender and Receiver must be different");

        Optional<Account> senderOpt = accountRepository.findByAccountNumber(transactionRequest.getSenderAccountNumber());
        Optional<Account> receiverOpt = accountRepository.findByAccountNumber(transactionRequest.getReceiverAccountNumber());

        //check existence
        Account sender = senderOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender Not Found"));
        Account receiver = receiverOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver Not Found"));

        //check balance
        if(senderOpt.get().getBalance().compareTo(transactionRequest.getAmount()) < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Sender's balance not enough");
        }

        sender.setBalance(sender.getBalance().subtract(transactionRequest.getAmount()));
        accountRepository.save(sender);

        receiver.setBalance(receiver.getBalance().add(transactionRequest.getAmount()));
        accountRepository.save(receiver);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccount(sender);
        senderTransaction.setAmount(transactionRequest.getAmount());
        senderTransaction.setTransactionType(TransactionType.SEND);
        transactionRepository.save(senderTransaction);

        Transaction receverTransaction = new Transaction();
        receverTransaction.setAccount(receiver);
        receverTransaction.setAmount(transactionRequest.getAmount());
        receverTransaction.setTransactionType(TransactionType.RECEIVE);
        transactionRepository.save(receverTransaction);

        return new TransactionResponse("Money send successful!");
    }
}
