package com.monese.test.integration;

import com.monese.test.dtos.AccountDto;
import com.monese.test.services.AccountService;
import com.monese.test.utils.Generator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testGetAllAccount(){
        AccountDto accountDto1 = accountService.createAccount(Generator.generateRandomAccountDto());
        AccountDto accountDto2 = accountService.createAccount(Generator.generateRandomAccountDto());

        List<AccountDto> accountDtos = accountService.getAllAccounts();

        Assertions.assertEquals(2, accountDtos.size());
        Assertions.assertTrue(accountDtos.contains(accountDto1));
        Assertions.assertTrue(accountDtos.contains(accountDto2));
    }

    @Test
    public void testCreateAccount(){
        AccountDto randomAccountDto = Generator.generateRandomAccountDto();
        AccountDto accountDto = accountService.createAccount(randomAccountDto);
        Assertions.assertEquals(randomAccountDto,(accountDto));
    }

    @Test
    public void receiverDoesNotExist(){
        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()->accountService.getAccountByAccountNumber("NotExist"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND,ex.getStatus());
    }
}
