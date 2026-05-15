package com.inditex.accounts.infrastructure.adapter.in.rest.exception;

public final class ErrorMessages {

    public static final String CUSTOMER_NOT_FOUND_TITLE = "Cliente no encontrado";
    public static final String BANK_ACCOUNT_NOT_FOUND_TITLE = "Cuenta bancaria no encontrada";
    public static final String VALIDATION_ERROR_TITLE = "Error de validación";
    public static final String INVALID_FIELDS_DETAIL = "La solicitud contiene campos inválidos";
    public static final String INVALID_PARAMETERS_DETAIL = "La solicitud contiene parámetros inválidos";
    public static final String INVALID_REQUEST_BODY_TITLE = "Cuerpo de solicitud inválido";
    public static final String INVALID_REQUEST_BODY_DETAIL = "El cuerpo de la solicitud no tiene un formato válido o contiene valores no permitidos";
    public static final String INVALID_PARAMETER_TITLE = "Parámetro inválido";
    public static final String INVALID_PARAMETER_DETAIL_PREFIX = "El parámetro '";
    public static final String INVALID_PARAMETER_DETAIL_SUFFIX = "' tiene un valor inválido";
    public static final String INTERNAL_SERVER_ERROR_TITLE = "Error interno del servidor";
    public static final String INTERNAL_SERVER_ERROR_DETAIL = "Se ha producido un error inesperado";
    public static final String ERRORS_PROPERTY = "errors";
    public static final String INVALID_VALUE = "Valor inválido";

    private ErrorMessages() {
    }
}
