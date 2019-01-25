package com.monese.test.repositories;

import com.monese.test.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAll();

    Optional<Account> findByAccountNumber(String accountNumber);
}