package com.inditex.accounts.infrastructure.adapter.in.rest.mapper;

import com.inditex.accounts.domain.model.Cliente;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.ClienteResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteRestMapper {

    private final CuentaBancariaRestMapper cuentaBancariaRestMapper;

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getApellido1(),
                cliente.getApellido2(),
                cliente.getFechaNacimiento(),
                cliente.getCuentas().stream().map(cuentaBancariaRestMapper::toResponse).toList()
        );
    }

    public PageResponse<ClienteResponse> toPageResponse(Page<Cliente> page) {
        return new PageResponse<>(
                page.getContent().stream().map(this::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious(),
                page.isEmpty()
        );
    }
}
