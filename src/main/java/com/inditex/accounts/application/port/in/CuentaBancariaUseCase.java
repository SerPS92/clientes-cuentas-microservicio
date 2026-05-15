package com.inditex.accounts.application.port.in;

import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.domain.model.TipoCuenta;

import java.math.BigDecimal;
import java.util.Optional;

public interface CuentaBancariaUseCase {

    Optional<CuentaBancaria> createCuentaBancaria(String dniCliente, TipoCuenta tipoCuenta, BigDecimal total);

    Optional<CuentaBancaria> updateCuentaBancariaTotal(Long idCuenta, BigDecimal total);
}
