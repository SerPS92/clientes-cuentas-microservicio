package com.inditex.accounts.application.service;

import com.inditex.accounts.application.port.in.CuentaBancariaUseCase;
import com.inditex.accounts.application.port.out.ClienteRepositoryPort;
import com.inditex.accounts.application.port.out.CuentaBancariaRepositoryPort;
import com.inditex.accounts.domain.exception.ClienteNotFoundException;
import com.inditex.accounts.domain.exception.CuentaBancariaNotFoundException;
import com.inditex.accounts.domain.model.Cliente;
import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.domain.model.TipoCuenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaBancariaService implements CuentaBancariaUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final CuentaBancariaRepositoryPort cuentaBancariaRepositoryPort;

    @Override
    public CuentaBancaria createCuentaBancaria(String dniCliente, TipoCuenta tipoCuenta, BigDecimal total) {
        Cliente cliente = clienteRepositoryPort.findByDni(dniCliente)
                .orElseThrow(() -> new ClienteNotFoundException(dniCliente));

        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .cliente(cliente)
                .tipoCuenta(tipoCuenta)
                .total(total)
                .build();

        return cuentaBancariaRepositoryPort.save(cuentaBancaria);
    }

    @Override
    public CuentaBancaria updateCuentaBancariaTotal(Long idCuenta, BigDecimal total) {
        return cuentaBancariaRepositoryPort.findById(idCuenta)
                .map(cuenta -> {
                    cuenta.setTotal(total);
                    return cuentaBancariaRepositoryPort.save(cuenta);
                })
                .orElseThrow(() -> new CuentaBancariaNotFoundException(idCuenta));
    }
}
