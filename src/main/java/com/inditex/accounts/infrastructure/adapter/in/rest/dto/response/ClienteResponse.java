package com.inditex.accounts.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Respuesta con los datos de un cliente y sus cuentas bancarias")
public record ClienteResponse(
        @Schema(description = "DNI del cliente", example = "11111111A")
        String dni,
        @Schema(description = "Nombre del cliente", example = "Juan")
        String nombre,
        @Schema(description = "Primer apellido del cliente", example = "Pérez")
        String apellido1,
        @Schema(description = "Segundo apellido del cliente", example = "López")
        String apellido2,
        @Schema(description = "Fecha de nacimiento del cliente", example = "1959-09-12")
        LocalDate fechaNacimiento,
        @Schema(description = "Cuentas bancarias asociadas al cliente")
        List<CuentaBancariaResponse> cuentas
) {
}
