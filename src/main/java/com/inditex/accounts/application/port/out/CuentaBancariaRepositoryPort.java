package com.inditex.accounts.application.port.out;

import com.inditex.accounts.domain.model.CuentaBancaria;

import java.util.Optional;

public interface CuentaBancariaRepositoryPort {

    CuentaBancaria save(CuentaBancaria cuentaBancaria);

    Optional<CuentaBancaria> findById(Long id);
}
