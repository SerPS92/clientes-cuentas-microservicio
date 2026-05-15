package com.inditex.accounts.domain.exception;

public class CuentaBancariaNotFoundException extends RuntimeException {

    public CuentaBancariaNotFoundException(Long id) {
        super("No existe ninguna cuenta bancaria con id " + id);
    }
}
