package com.inditex.accounts.application.port.in;

import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.domain.model.TipoCuenta;

import java.math.BigDecimal;

public interface CuentaBancariaUseCase {

    CuentaBancaria createCuentaBancaria(String dniCliente, TipoCuenta tipoCuenta, BigDecimal total);

    CuentaBancaria updateCuentaBancariaTotal(Long idCuenta, BigDecimal total);
}
