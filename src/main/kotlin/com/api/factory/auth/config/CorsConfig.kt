package com.api.factory.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowCredentials(true)
            .allowedOriginPatterns("*")
            .allowedHeaders(
                "*",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin",
                "Authorization",
                "Content-Disposition",
                "Cache-Control",
                "Content-Type",
            ).exposedHeaders(
                "Access-Control-Allow-Origin",
                "Content-Type",
                "Content-Disposition",
                "SID",
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    }
}
