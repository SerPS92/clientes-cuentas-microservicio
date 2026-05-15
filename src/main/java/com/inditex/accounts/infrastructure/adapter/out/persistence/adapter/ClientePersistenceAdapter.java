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
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientePersistenceAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteJpaRepository;

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        Page<Cliente> page = clienteJpaRepository.findAll(pageable);
        loadCuentas(page);
        return page;
    }

    @Override
    public Optional<Cliente> findByDni(String dni) {
        return clienteJpaRepository.findByDniWithCuentas(dni);
    }

    @Override
    public Page<Cliente> findClientesMayoresDeEdad(LocalDate fechaLimite, Pageable pageable) {
        Page<Cliente> page = clienteJpaRepository.findClientesMayoresDeEdad(fechaLimite, pageable);
        loadCuentas(page);
        return page;
    }

    @Override
    public Page<Cliente> findClientesConTotalCuentasSuperiorA(BigDecimal cantidad, Pageable pageable) {
        Page<Cliente> page = clienteJpaRepository.findClientesConTotalCuentasSuperiorA(cantidad, pageable);
        loadCuentas(page);
        return page;
    }

    private void loadCuentas(Page<Cliente> page) {
        List<String> dnis = page.getContent().stream()
                .map(Cliente::getDni)
                .toList();

        if (!dnis.isEmpty()) {
            // Carga explícitamente la relación lazy para evitar LazyInitializationException con open-in-view=false.
            clienteJpaRepository.findAllWithCuentasByDniIn(dnis);
        }
    }
}
