package com.inditex.accounts.infrastructure.adapter.in.rest.exception;

import com.inditex.accounts.domain.exception.ClienteNotFoundException;
import com.inditex.accounts.domain.exception.CuentaBancariaNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ProblemDetail handleClienteNotFound(ClienteNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle(ErrorMessages.CUSTOMER_NOT_FOUND_TITLE);
        return problemDetail;
    }

    @ExceptionHandler(CuentaBancariaNotFoundException.class)
    public ProblemDetail handleCuentaBancariaNotFound(CuentaBancariaNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle(ErrorMessages.BANK_ACCOUNT_NOT_FOUND_TITLE);
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS_DETAIL);
        problemDetail.setTitle(ErrorMessages.VALIDATION_ERROR_TITLE);

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ErrorMessages.INVALID_VALUE,
                        (existing, replacement) -> existing
                ));
        problemDetail.setProperty(ErrorMessages.ERRORS_PROPERTY, errors);
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_PARAMETERS_DETAIL);
        problemDetail.setTitle(ErrorMessages.VALIDATION_ERROR_TITLE);

        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        problemDetail.setProperty(ErrorMessages.ERRORS_PROPERTY, errors);
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ignored) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.INVALID_REQUEST_BODY_DETAIL
        );
        problemDetail.setTitle(ErrorMessages.INVALID_REQUEST_BODY_TITLE);
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.INVALID_PARAMETER_DETAIL_PREFIX + ex.getName() + ErrorMessages.INVALID_PARAMETER_DETAIL_SUFFIX
        );
        problemDetail.setTitle(ErrorMessages.INVALID_PARAMETER_TITLE);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ignored) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessages.INTERNAL_SERVER_ERROR_DETAIL
        );
        problemDetail.setTitle(ErrorMessages.INTERNAL_SERVER_ERROR_TITLE);
        return problemDetail;
    }
}
