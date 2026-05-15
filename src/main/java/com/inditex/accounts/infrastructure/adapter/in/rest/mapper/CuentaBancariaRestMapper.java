package com.inditex.accounts.infrastructure.adapter.in.rest.mapper;

import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.CuentaBancariaResponse;
import org.springframework.stereotype.Component;

@Component
public class CuentaBancariaRestMapper {

    public CuentaBancariaResponse toResponse(CuentaBancaria cuentaBancaria) {
        return new CuentaBancariaResponse(
                cuentaBancaria.getId(),
                cuentaBancaria.getTipoCuenta(),
                cuentaBancaria.getTotal()
        );
    }
}
