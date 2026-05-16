package com.inditex.accounts.infrastructure.adapter.in.rest.dto.request;

import com.inditex.accounts.domain.model.TipoCuenta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(description = "Solicitud para crear una cuenta bancaria")
public record CreateCuentaBancariaRequest(
        @NotBlank
        @Schema(description = "DNI del cliente asociado a la cuenta bancaria", example = "11111111A")
        String dniCliente,
        @NotNull
        @Schema(
                description = "Tipo de cuenta bancaria",
                example = "NORMAL",
                allowableValues = {"PREMIUM", "NORMAL", "JUNIOR"}
        )
        TipoCuenta tipoCuenta,
        @NotNull
        @PositiveOrZero
        @Schema(description = "Saldo inicial de la cuenta bancaria", example = "50000.00")
        BigDecimal total
) {
}
