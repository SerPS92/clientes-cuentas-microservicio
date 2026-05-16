package com.inditex.accounts.infrastructure.adapter.in.rest.api;

import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.ClienteResponse;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones para consultar clientes y sus cuentas bancarias")
public interface ClienteApi {

    @GetMapping
    @Operation(summary = "Obtener clientes", description = "Devuelve un listado paginado de clientes junto con sus cuentas bancarias asociadas. Admite parámetros de paginación page, size y sort.")
    @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<PageResponse<ClienteResponse>> getClientes(
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "dni", direction = Sort.Direction.ASC)
            Pageable pageable
    );

    @GetMapping("/mayores-de-edad")
    @Operation(summary = "Obtener clientes mayores de edad", description = "Devuelve un listado paginado de clientes cuya fecha de nacimiento indica una edad igual o superior a 18 años. Admite parámetros de paginación page, size y sort.")
    @ApiResponse(responseCode = "200", description = "Clientes mayores de edad obtenidos correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<PageResponse<ClienteResponse>> getClientesMayoresDeEdad(
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "dni", direction = Sort.Direction.ASC)
            Pageable pageable
    );

    @GetMapping("/con-cuenta-superior-a/{cantidad}")
    @Operation(summary = "Obtener clientes con cuentas superiores a una cantidad", description = "Devuelve un listado paginado de clientes cuya suma total de saldos en cuentas bancarias sea estrictamente superior a la cantidad indicada. El cálculo se realiza en base de datos. Admite parámetros de paginación page, size y sort.")
    @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Cantidad o parámetros de paginación inválidos",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<PageResponse<ClienteResponse>> getClientesConCuentaSuperiorA(
            @Parameter(description = "Cantidad mínima que debe superar la suma total de las cuentas del cliente", required = true, example = "50000.00")
            @PathVariable
            @PositiveOrZero
            BigDecimal cantidad,
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "dni", direction = Sort.Direction.ASC)
            Pageable pageable
    );

    @GetMapping("/{dni}")
    @Operation(summary = "Obtener cliente por DNI", description = "Devuelve los datos de un cliente junto con sus cuentas bancarias asociadas.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<ClienteResponse> getClienteByDni(
            @Parameter(description = "DNI del cliente", required = true, example = "11111111A")
            @PathVariable
            String dni
    );
}
