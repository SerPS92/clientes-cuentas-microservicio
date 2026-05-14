package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import com.inditex.accounts.infrastructure.adapter.in.rest.api.CuentaBancariaApi;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.CreateCuentaBancariaRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.UpdateCuentaBancariaTotalRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.CuentaBancariaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CuentaBancariaController implements CuentaBancariaApi {
    @Override
    public ResponseEntity<CuentaBancariaResponse> createCuentaBancaria(CreateCuentaBancariaRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<CuentaBancariaResponse> updateCuentaBancariaTotal(Long idCuenta, UpdateCuentaBancariaTotalRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
