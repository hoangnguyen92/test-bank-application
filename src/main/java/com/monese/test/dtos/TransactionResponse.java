package com.monese.test.dtos;

public class TransactionResponse {

    private String message;

    public TransactionResponse() {
    }

    public TransactionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
