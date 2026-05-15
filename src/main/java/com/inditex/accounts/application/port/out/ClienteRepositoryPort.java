package com.inditex.accounts.application.port.out;

import com.inditex.accounts.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface ClienteRepositoryPort {

    Page<Cliente> findAll(Pageable pageable);

    Optional<Cliente> findByDni(String dni);

    Page<Cliente> findClientesMayoresDeEdad(LocalDate fechaLimite, Pageable pageable);

    Page<Cliente> findClientesConTotalCuentasSuperiorA(BigDecimal cantidad, Pageable pageable);
}
