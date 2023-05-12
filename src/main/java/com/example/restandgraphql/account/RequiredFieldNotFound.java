package com.example.restandgraphql.account;

public class RequiredFieldNotFound extends RuntimeException {
    RequiredFieldNotFound(String message) {
        super(message);
    }
}
