package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import com.inditex.accounts.infrastructure.adapter.in.rest.api.ClienteApi;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.ClienteResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Validated
public class ClienteController implements ClienteApi {
    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientes(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientesMayoresDeEdad(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientesConCuentaSuperiorA(BigDecimal cantidad, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<ClienteResponse> getClienteByDni(String dni) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
