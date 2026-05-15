package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import com.inditex.accounts.application.port.in.ClienteUseCase;
import com.inditex.accounts.domain.model.Cliente;
import com.inditex.accounts.infrastructure.adapter.in.rest.api.ClienteApi;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.ClienteResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.PageResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.mapper.ClienteRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Validated
@RequiredArgsConstructor
public class ClienteController implements ClienteApi {

    private final ClienteUseCase clienteUseCase;
    private final ClienteRestMapper clienteRestMapper;

    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientes(Pageable pageable) {
        Page<Cliente> page = clienteUseCase.getClientes(pageable);
        return ResponseEntity.ok(clienteRestMapper.toPageResponse(page));
    }

    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientesMayoresDeEdad(Pageable pageable) {
        Page<Cliente> page = clienteUseCase.getClientesMayoresDeEdad(pageable);
        return ResponseEntity.ok(clienteRestMapper.toPageResponse(page));
    }

    @Override
    public ResponseEntity<PageResponse<ClienteResponse>> getClientesConCuentaSuperiorA(BigDecimal cantidad, Pageable pageable) {
        Page<Cliente> page = clienteUseCase.getClientesConTotalCuentasSuperiorA(cantidad, pageable);
        return ResponseEntity.ok(clienteRestMapper.toPageResponse(page));
    }

    @Override
    public ResponseEntity<ClienteResponse> getClienteByDni(String dni) {
        Cliente cliente = clienteUseCase.getClienteByDni(dni);
        return ResponseEntity.ok(clienteRestMapper.toResponse(cliente));
    }
}
