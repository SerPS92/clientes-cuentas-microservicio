package com.inditex.accounts.application.service;

import com.inditex.accounts.application.port.out.ClienteRepositoryPort;
import com.inditex.accounts.domain.exception.ClienteNotFoundException;
import com.inditex.accounts.domain.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Should return paginated customers")
    void getClientes_ok() {
        Pageable pageable = PageRequest.of(0, 10);
        Cliente cliente1 = Cliente.builder()
                .dni("11111111A")
                .nombre("Juan")
                .apellido1("Pérez")
                .apellido2("López")
                .fechaNacimiento(LocalDate.of(1959, 9, 12))
                .build();
        Page<Cliente> page = new PageImpl<>(List.of(cliente1), pageable, 1);
        when(clienteRepositoryPort.findAll(pageable)).thenReturn(page);

        Page<Cliente> result = clienteService.getClientes(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return adult customers")
    void getClientesMayoresDeEdad_ok() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> page = new PageImpl<>(List.of(), pageable, 0);
        when(clienteRepositoryPort.findClientesMayoresDeEdad(any(LocalDate.class), eq(pageable))).thenReturn(page);

        Page<Cliente> result = clienteService.getClientesMayoresDeEdad(pageable);

        assertThat(result).isSameAs(page);
    }

    @Test
    @DisplayName("Should return customers with total bank account balance greater than amount")
    void getClientesConTotalCuentasSuperiorA_ok() {
        Pageable pageable = PageRequest.of(0, 10);
        BigDecimal cantidad = new BigDecimal("50000.00");
        Page<Cliente> page = new PageImpl<>(List.of(), pageable, 0);
        when(clienteRepositoryPort.findClientesConTotalCuentasSuperiorA(cantidad, pageable)).thenReturn(page);

        Page<Cliente> result = clienteService.getClientesConTotalCuentasSuperiorA(cantidad, pageable);

        assertThat(result).isSameAs(page);
    }

    @Test
    @DisplayName("Should return customer by DNI when exists")
    void getClienteByDni_ok() {
        String dni = "11111111A";
        Cliente cliente = Cliente.builder()
                .dni(dni)
                .nombre("Juan")
                .apellido1("Pérez")
                .apellido2("López")
                .fechaNacimiento(LocalDate.of(1959, 9, 12))
                .build();
        when(clienteRepositoryPort.findByDni(dni)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteByDni(dni);

        assertThat(result.getDni()).isEqualTo(dni);
    }

    @Test
    @DisplayName("Should throw ClienteNotFoundException when customer does not exist")
    void getClienteByDni_notFound() {
        String dni = "99999999Z";
        when(clienteRepositoryPort.findByDni(dni)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getClienteByDni(dni))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining(dni);
    }
}
