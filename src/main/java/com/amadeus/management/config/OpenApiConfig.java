package com.amadeus.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI managementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Management API")
                        .description("API Rest para manejo básico de usuarios")
                        .version("0.0.1")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .servers(List.of(
                        new Server().url("/").description("Default server")
                ));
    }

    @Bean
    public GroupedOpenApi modulesGroup() {
        return GroupedOpenApi.builder()
                .group("modules")
                .packagesToScan("com.amadeus.management.modules")
                .build();
    }
}
