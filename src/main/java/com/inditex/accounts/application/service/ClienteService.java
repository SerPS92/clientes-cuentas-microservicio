package com.inditex.accounts.application.service;

import com.inditex.accounts.application.port.in.ClienteUseCase;
import com.inditex.accounts.application.port.out.ClienteRepositoryPort;
import com.inditex.accounts.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteService implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

    @Override
    public Page<Cliente> getClientes(Pageable pageable) {
        return clienteRepositoryPort.findAll(pageable);
    }

    @Override
    public Page<Cliente> getClientesMayoresDeEdad(Pageable pageable) {
        LocalDate fechaLimite = LocalDate.now().minusYears(18);
        return clienteRepositoryPort.findClientesMayoresDeEdad(fechaLimite, pageable);
    }

    @Override
    public Page<Cliente> getClientesConTotalCuentasSuperiorA(BigDecimal cantidad, Pageable pageable) {
        return clienteRepositoryPort.findClientesConTotalCuentasSuperiorA(cantidad, pageable);
    }

    @Override
    public Optional<Cliente> getClienteByDni(String dni) {
        return clienteRepositoryPort.findByDni(dni);
    }
}
