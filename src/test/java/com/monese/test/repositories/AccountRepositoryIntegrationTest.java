package com.monese.test.repositories;

import com.monese.test.model.Account;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
        entityManager.persist(generateRandomAccount());
        entityManager.persist(generateRandomAccount());
        entityManager.flush();

        List<Account> accounts = accountRepository.findAll();

        assertEquals(2,accounts.size());
    }

    @Test
    public void testFindAccountByAccountNumber(){
        Account persistedAccount = entityManager.persist(generateRandomAccount());
        entityManager.flush();

        Optional<Account> account = accountRepository.findByAccountNumber(persistedAccount.getAccountNumber());

        assertTrue(account.isPresent());
        assertEquals(persistedAccount,account.get());
    }

    private Account generateRandomAccount() {
        Account account = new Account();
        account.setAccountNumber(RandomStringUtils.randomAlphanumeric(15));
        account.setFirstName(RandomStringUtils.randomAlphabetic(5));
        account.setLastName(RandomStringUtils.randomAlphabetic(5));
        account.setBalance(new BigDecimal(1000));
        account.setEmail(RandomStringUtils.randomAlphabetic(20));
        return account;
    }
}
