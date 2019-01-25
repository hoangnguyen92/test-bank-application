package com.monese.test.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AccountDto {
    private String accountNumber;

    private String firstName;

    private String lastName;

    private String email;

    private BigDecimal balance;

    private List<TransactionDto> transactions;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountDto that = (AccountDto) o;
        return Objects.equals(accountNumber, that.accountNumber)
                                && Objects.equals(firstName, that.firstName)
                                && Objects.equals(lastName, that.lastName)
                                && Objects.equals(email, that.email)
                                && Objects.equals(balance, that.balance)
                                && Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, firstName,lastName,email,balance,transactions);
    }
}
