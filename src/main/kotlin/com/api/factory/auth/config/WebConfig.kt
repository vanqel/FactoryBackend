package com.api.factory.auth.config

import com.api.factory.auth.filter.JWTAuthorizationFilter
import com.api.factory.auth.services.security.app.AppAuthenticationManager
import com.api.factory.auth.services.security.auth.AuthProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebConfig(
    val authenticationManager: AppAuthenticationManager,
    val authProvider: AuthProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/user/register", "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/token/validate", "/token/validateUser").authenticated()
                    .requestMatchers(HttpMethod.OPTIONS).authenticated()
                    .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()
                    .requestMatchers(HttpMethod.GET, "/kassa/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/kassa/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/useroperations").authenticated()
                    .anyRequest().permitAll()
            }
            .addFilter(
                JWTAuthorizationFilter(
                    authenticationManager,
                    authProvider,
                ),
            )
            .logout {
                it
                    .addLogoutHandler(SecurityContextLogoutHandler())
                    .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .permitAll()
            }
            .build()
    }
}
