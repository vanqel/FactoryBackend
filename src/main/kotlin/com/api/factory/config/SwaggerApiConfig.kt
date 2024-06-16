package com.api.factory.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    servers = [
        Server(
            url = "http://localhost:8099",
            description = "Local Server"
        ),
        Server(
            url = "http://109.120.187.242:8099",
            description = "Web Server"
        ),
    ]
)
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class SwaggerApiConfig
