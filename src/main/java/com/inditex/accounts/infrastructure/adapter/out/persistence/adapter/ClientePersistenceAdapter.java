package com.inditex.accounts.infrastructure.adapter.out.persistence.adapter;

import com.inditex.accounts.application.port.out.ClienteRepositoryPort;
import com.inditex.accounts.domain.model.Cliente;
import com.inditex.accounts.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientePersistenceAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteJpaRepository;

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Cliente> findByDni(String dni) {
        return clienteJpaRepository.findById(dni);
    }

    @Override
    public Page<Cliente> findClientesMayoresDeEdad(LocalDate fechaLimite, Pageable pageable) {
        return clienteJpaRepository.findClientesMayoresDeEdad(fechaLimite, pageable);
    }

    @Override
    public Page<Cliente> findClientesConTotalCuentasSuperiorA(BigDecimal cantidad, Pageable pageable) {
        return clienteJpaRepository.findClientesConTotalCuentasSuperiorA(cantidad, pageable);
    }
}
