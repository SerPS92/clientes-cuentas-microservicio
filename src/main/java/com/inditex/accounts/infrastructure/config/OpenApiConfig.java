package com.inditex.accounts.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clientesCuentasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clientes Cuentas Microservicio API")
                        .description("API REST para la gestión de clientes y cuentas bancarias.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sergio")
                                .url("https://github.com/SerPS92/clientes-cuentas-microservicio")));
    }
}
