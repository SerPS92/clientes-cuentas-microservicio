package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import com.inditex.accounts.application.port.in.CuentaBancariaUseCase;
import com.inditex.accounts.infrastructure.adapter.in.rest.api.CuentaBancariaApi;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.CreateCuentaBancariaRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.UpdateCuentaBancariaTotalRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.CuentaBancariaResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.mapper.CuentaBancariaRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
public class CuentaBancariaController implements CuentaBancariaApi {

    private final CuentaBancariaUseCase cuentaBancariaUseCase;
    private final CuentaBancariaRestMapper cuentaBancariaRestMapper;

    @Override
    public ResponseEntity<CuentaBancariaResponse> createCuentaBancaria(CreateCuentaBancariaRequest request) {
        return cuentaBancariaUseCase.createCuentaBancaria(
                        request.dniCliente(),
                        request.tipoCuenta(),
                        request.total())
                .map(cuentaBancariaRestMapper::toResponse)
                .map(response -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(response.id())
                            .toUri();
                    return ResponseEntity.created(location).body(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<CuentaBancariaResponse> updateCuentaBancariaTotal(Long idCuenta, UpdateCuentaBancariaTotalRequest request) {
        return cuentaBancariaUseCase.updateCuentaBancariaTotal(idCuenta, request.total())
                .map(cuentaBancariaRestMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
