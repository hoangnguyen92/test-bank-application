package com.monese.test.integration;

import com.monese.test.model.Account;
import com.monese.test.repositories.AccountRepository;
import com.monese.test.utils.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindAllAccounts(){
        entityManager.persist(Generator.generateRandomAccount());
        entityManager.persist(Generator.generateRandomAccount());
        entityManager.flush();

        List<Account> accounts = accountRepository.findAll();

        assertEquals(2,accounts.size());
    }

    @Test
    public void testFindAccountByAccountNumber(){
        Account persistedAccount = entityManager.persist(Generator.generateRandomAccount());
        entityManager.flush();

        Optional<Account> account = accountRepository.findByAccountNumber(persistedAccount.getAccountNumber());

        assertTrue(account.isPresent());
        assertEquals(persistedAccount,account.get());
    }


}
