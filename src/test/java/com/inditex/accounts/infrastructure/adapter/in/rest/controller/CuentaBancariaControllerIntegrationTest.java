package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.accounts.domain.model.TipoCuenta;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.CreateCuentaBancariaRequest;
import com.inditex.accounts.infrastructure.adapter.in.rest.dto.request.UpdateCuentaBancariaTotalRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CuentaBancariaControllerIntegrationTest {

    private static final String VALIDATION_ERROR_TITLE = "Error de validación";
    private static final String INVALID_REQUEST_BODY_TITLE = "Cuerpo de solicitud inválido";
    private static final String INVALID_PARAMETER_TITLE = "Parámetro inválido";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create bank account")
    void createCuentaBancaria_ok() throws Exception {
        CreateCuentaBancariaRequest request = new CreateCuentaBancariaRequest(
                "11111111A",
                TipoCuenta.NORMAL,
                new BigDecimal("50000.00")
        );

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.tipoCuenta").value("NORMAL"))
                .andExpect(jsonPath("$.total").value(50000.0));
    }

    @Test
    @DisplayName("Should return 404 when creating bank account for non-existing customer")
    void createCuentaBancaria_customerNotFound() throws Exception {
        CreateCuentaBancariaRequest request = new CreateCuentaBancariaRequest(
                "99999999Z",
                TipoCuenta.NORMAL,
                new BigDecimal("50000.00")
        );

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Cliente no encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateBankAccountRequests")
    @DisplayName("Should return 400 when creating bank account with invalid request")
    void createCuentaBancaria_badRequestCases(Object bodySource, String expectedTitle) throws Exception {
        String body = bodySource instanceof String rawJson
                ? rawJson
                : objectMapper.writeValueAsString(bodySource);

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedTitle))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Should update bank account balance")
    void updateCuentaBancariaTotal_ok() throws Exception {
        UpdateCuentaBancariaTotalRequest request = new UpdateCuentaBancariaTotalRequest(new BigDecimal("180000.00"));

        mockMvc.perform(put("/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipoCuenta").value("PREMIUM"))
                .andExpect(jsonPath("$.total").value(180000.0));
    }

    @Test
    @DisplayName("Should return 404 when updating non-existing bank account")
    void updateCuentaBancariaTotal_notFound() throws Exception {
        UpdateCuentaBancariaTotalRequest request = new UpdateCuentaBancariaTotalRequest(new BigDecimal("180000.00"));

        mockMvc.perform(put("/cuentas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Cuenta bancaria no encontrada"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Should return 400 with constraint violation when updating bank account with zero id")
    void updateCuentaBancariaTotal_constraintViolation_zeroId() throws Exception {
        UpdateCuentaBancariaTotalRequest request = new UpdateCuentaBancariaTotalRequest(new BigDecimal("180000.00"));

        mockMvc.perform(put("/cuentas/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(VALIDATION_ERROR_TITLE))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUpdateBankAccountRequests")
    @DisplayName("Should return 400 when updating bank account with invalid request")
    void updateCuentaBancariaTotal_badRequestCases(String url, Object bodySource, String expectedTitle) throws Exception {
        String body = bodySource instanceof String rawJson
                ? rawJson
                : objectMapper.writeValueAsString(bodySource);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedTitle))
                .andExpect(jsonPath("$.status").value(400));
    }

    private static Stream<Arguments> provideInvalidCreateBankAccountRequests() {
        return Stream.of(
                Arguments.of(
                        new CreateCuentaBancariaRequest("", TipoCuenta.NORMAL, new BigDecimal("50000.00")),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        new CreateCuentaBancariaRequest("11111111A", TipoCuenta.NORMAL, new BigDecimal("-10.00")),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        new CreateCuentaBancariaRequest("11111111A", TipoCuenta.NORMAL, null),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        new CreateCuentaBancariaRequest("11111111A", null, new BigDecimal("50000.00")),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        """
                        {
                          "dniCliente": "11111111A",
                          "tipoCuenta": "INVALIDA",
                          "total": 50000.00
                        }
                        """,
                        INVALID_REQUEST_BODY_TITLE
                )
        );
    }

    private static Stream<Arguments> provideInvalidUpdateBankAccountRequests() {
        return Stream.of(
                Arguments.of(
                        "/cuentas/1",
                        new UpdateCuentaBancariaTotalRequest(new BigDecimal("-1.00")),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        "/cuentas/1",
                        new UpdateCuentaBancariaTotalRequest(null),
                        VALIDATION_ERROR_TITLE
                ),
                Arguments.of(
                        "/cuentas/abc",
                        new UpdateCuentaBancariaTotalRequest(new BigDecimal("180000.00")),
                        INVALID_PARAMETER_TITLE
                )
        );
    }
}
