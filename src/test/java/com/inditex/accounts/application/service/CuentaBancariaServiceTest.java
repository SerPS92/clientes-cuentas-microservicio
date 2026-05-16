package com.inditex.accounts.application.service;

import com.inditex.accounts.application.port.out.ClienteRepositoryPort;
import com.inditex.accounts.application.port.out.CuentaBancariaRepositoryPort;
import com.inditex.accounts.domain.exception.ClienteNotFoundException;
import com.inditex.accounts.domain.exception.CuentaBancariaNotFoundException;
import com.inditex.accounts.domain.model.Cliente;
import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.domain.model.TipoCuenta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaBancariaServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @Mock
    private CuentaBancariaRepositoryPort cuentaBancariaRepositoryPort;

    @InjectMocks
    private CuentaBancariaService cuentaBancariaService;

    @Test
    @DisplayName("Should create bank account when customer exists")
    void createCuentaBancaria_ok() {
        String dniCliente = "11111111A";
        TipoCuenta tipoCuenta = TipoCuenta.NORMAL;
        BigDecimal total = new BigDecimal("50000.00");
        Cliente cliente = Cliente.builder()
                .dni(dniCliente)
                .nombre("Juan")
                .apellido1("Pérez")
                .apellido2("López")
                .fechaNacimiento(LocalDate.of(1959, 9, 12))
                .build();
        CuentaBancaria saved = CuentaBancaria.builder()
                .id(10L)
                .cliente(cliente)
                .tipoCuenta(tipoCuenta)
                .total(total)
                .build();
        when(clienteRepositoryPort.findByDni(dniCliente)).thenReturn(Optional.of(cliente));
        when(cuentaBancariaRepositoryPort.save(any(CuentaBancaria.class))).thenReturn(saved);

        CuentaBancaria result = cuentaBancariaService.createCuentaBancaria(dniCliente, tipoCuenta, total);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getCliente().getDni()).isEqualTo(dniCliente);
        assertThat(result.getTipoCuenta()).isEqualTo(tipoCuenta);
        assertThat(result.getTotal()).isEqualByComparingTo(total);
    }

    @Test
    @DisplayName("Should throw ClienteNotFoundException when creating bank account for non-existing customer")
    void createCuentaBancaria_customerNotFound() {
        String dniCliente = "99999999Z";
        TipoCuenta tipoCuenta = TipoCuenta.NORMAL;
        BigDecimal total = new BigDecimal("50000.00");
        when(clienteRepositoryPort.findByDni(dniCliente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaBancariaService.createCuentaBancaria(dniCliente, tipoCuenta, total))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining(dniCliente);

        verify(cuentaBancariaRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Should update bank account balance when account exists")
    void updateCuentaBancariaTotal_ok() {
        Long idCuenta = 1L;
        BigDecimal total = new BigDecimal("180000.00");
        Cliente cliente = Cliente.builder()
                .dni("11111111A")
                .nombre("Juan")
                .apellido1("Pérez")
                .apellido2("López")
                .fechaNacimiento(LocalDate.of(1959, 9, 12))
                .build();
        CuentaBancaria cuenta = CuentaBancaria.builder()
                .id(1L)
                .cliente(cliente)
                .tipoCuenta(TipoCuenta.PREMIUM)
                .total(new BigDecimal("150000.00"))
                .build();
        CuentaBancaria saved = CuentaBancaria.builder()
                .id(1L)
                .cliente(cliente)
                .tipoCuenta(TipoCuenta.PREMIUM)
                .total(new BigDecimal("180000.00"))
                .build();
        when(cuentaBancariaRepositoryPort.findById(idCuenta)).thenReturn(Optional.of(cuenta));
        when(cuentaBancariaRepositoryPort.save(cuenta)).thenReturn(saved);

        CuentaBancaria result = cuentaBancariaService.updateCuentaBancariaTotal(idCuenta, total);

        assertThat(result.getId()).isEqualTo(idCuenta);
        assertThat(result.getTotal()).isEqualByComparingTo(total);
    }

    @Test
    @DisplayName("Should throw CuentaBancariaNotFoundException when bank account does not exist")
    void updateCuentaBancariaTotal_notFound() {
        Long idCuenta = 999L;
        BigDecimal total = new BigDecimal("180000.00");
        when(cuentaBancariaRepositoryPort.findById(idCuenta)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaBancariaService.updateCuentaBancariaTotal(idCuenta, total))
                .isInstanceOf(CuentaBancariaNotFoundException.class)
                .hasMessageContaining("999");

        verify(cuentaBancariaRepositoryPort, never()).save(any());
    }
}
