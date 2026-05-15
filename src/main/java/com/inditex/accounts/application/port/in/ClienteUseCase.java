package com.inditex.accounts.application.port.in;

import com.inditex.accounts.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ClienteUseCase {

    Page<Cliente> getClientes(Pageable pageable);

    Page<Cliente> getClientesMayoresDeEdad(Pageable pageable);

    Page<Cliente> getClientesConTotalCuentasSuperiorA(BigDecimal cantidad, Pageable pageable);

    Cliente getClienteByDni(String dni);
}
