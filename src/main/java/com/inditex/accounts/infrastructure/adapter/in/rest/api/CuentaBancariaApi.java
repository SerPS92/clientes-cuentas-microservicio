package com.inditex.accounts.infrastructure.adapter.in.rest.api;

import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.CreateCuentaBancariaRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.UpdateCuentaBancariaTotalRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.CuentaBancariaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/cuentas")
@Tag(name = "Cuentas bancarias", description = "Operaciones para crear y actualizar cuentas bancarias")
public interface CuentaBancariaApi {

    @PostMapping
    @Operation(summary = "Crear cuenta bancaria", description = "Crea una nueva cuenta bancaria asociada a un cliente existente.")
    @ApiResponse(responseCode = "201", description = "Cuenta bancaria creada correctamente",
            headers = @Header(name = "Location", description = "URI de la cuenta bancaria creada"),
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CuentaBancariaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<CuentaBancariaResponse> createCuentaBancaria(
            @Valid
            @RequestBody
            CreateCuentaBancariaRequest request
    );

    @PutMapping("/{idCuenta}")
    @Operation(summary = "Actualizar saldo de cuenta bancaria", description = "Actualiza el saldo total de una cuenta bancaria existente.")
    @ApiResponse(responseCode = "200", description = "Cuenta bancaria actualizada correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CuentaBancariaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<CuentaBancariaResponse> updateCuentaBancariaTotal(
            @Parameter(description = "Identificador de la cuenta bancaria", required = true, example = "1")
            @PathVariable
            @Positive
            Long idCuenta,
            @Valid
            @RequestBody
            UpdateCuentaBancariaTotalRequest request
    );
}
