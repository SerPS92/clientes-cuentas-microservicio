package com.inditex.accounts.infrastructure.adapter.in.rest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("providePaginatedCustomerEndpoints")
    @DisplayName("Should return paginated customer endpoints")
    void getClientesPaginatedEndpoints_ok(String url, int expectedTotalElements, int expectedContentLength) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedContentLength))
                .andExpect(jsonPath("$.totalElements").value(expectedTotalElements))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    @DisplayName("Should return customer by DNI")
    void getClienteByDni_ok() throws Exception {
        mockMvc.perform(get("/clientes/11111111A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("11111111A"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido1").value("Pérez"))
                .andExpect(jsonPath("$.apellido2").value("López"))
                .andExpect(jsonPath("$.cuentas").isArray())
                .andExpect(jsonPath("$.cuentas.length()").value(2));
    }

    @Test
    @DisplayName("Should return 404 when customer does not exist")
    void getClienteByDni_notFound() throws Exception {
        mockMvc.perform(get("/clientes/99999999Z"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Cliente no encontrado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("No existe ningún cliente con DNI 99999999Z"));
    }

    private static Stream<Arguments> providePaginatedCustomerEndpoints() {
        return Stream.of(
                Arguments.of("/clientes?page=0&size=10&sort=dni,asc", 5, 5),
                Arguments.of("/clientes/mayores-de-edad?page=0&size=10&sort=dni,asc", 4, 4),
                Arguments.of("/clientes/con-cuenta-superior-a/50000?page=0&size=10&sort=dni,asc", 4, 4)
        );
    }
}
