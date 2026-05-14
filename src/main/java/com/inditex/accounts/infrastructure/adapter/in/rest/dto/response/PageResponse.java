package com.inditex.accounts.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta paginada")
public record PageResponse<T>(
        @Schema(description = "Contenido de la página")
        List<T> content,
        @Schema(description = "Número de página actual", example = "0")
        int page,
        @Schema(description = "Tamaño de página", example = "10")
        int size,
        @Schema(description = "Número total de elementos", example = "25")
        long totalElements,
        @Schema(description = "Número total de páginas", example = "3")
        int totalPages,
        @Schema(description = "Indica si es la primera página", example = "true")
        boolean first,
        @Schema(description = "Indica si es la última página", example = "false")
        boolean last,
        @Schema(description = "Indica si existe una página siguiente", example = "true")
        boolean hasNext,
        @Schema(description = "Indica si existe una página anterior", example = "false")
        boolean hasPrevious,
        @Schema(description = "Indica si la página no contiene elementos", example = "false")
        boolean empty
) {
}
