package com.inditex.accounts.infrastructure.adapter.in.rest.dto.response;

import com.inditex.accounts.domain.model.TipoCuenta;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Respuesta con los datos de una cuenta bancaria")
public record CuentaBancariaResponse(
        @Schema(description = "Identificador de la cuenta bancaria", example = "1")
        Long id,
        @Schema(description = "Tipo de cuenta bancaria", example = "PREMIUM")
        TipoCuenta tipoCuenta,
        @Schema(description = "Saldo total de la cuenta bancaria", example = "150000.00")
        BigDecimal total
) {
}
