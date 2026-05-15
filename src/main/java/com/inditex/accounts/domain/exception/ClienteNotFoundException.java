package com.inditex.accounts.domain.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String dni) {
        super("No existe ningún cliente con DNI " + dni);
    }
}
