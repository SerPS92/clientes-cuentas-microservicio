package com.inditex.accounts.infrastructure.adapter.in.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(description = "Solicitud para actualizar el saldo total de una cuenta bancaria")
public record UpdateCuentaBancariaTotalRequest(
        @NotNull
        @PositiveOrZero
        @Schema(description = "Nuevo saldo total de la cuenta bancaria", example = "75000.00")
        BigDecimal total
) {
}
