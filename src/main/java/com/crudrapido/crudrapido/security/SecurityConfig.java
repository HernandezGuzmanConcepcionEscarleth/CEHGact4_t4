package com.crudrapido.crudrapido.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// indicamos que esta clase contiene la configuracion de seguridad
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // define que rutas son publicas y cuales necesitan un token para acceder
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
            // desactivamos csrf porque trabajamos con una api rest y tokens
            .csrf(csrf -> csrf.disable())

            // indicamos que no se van a guardar sesiones de los usuarios
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // configuramos los permisos para acceder a las rutas
            .authorizeHttpRequests(auth -> auth

                // permitimos el registro y el login sin necesidad de un token
                .requestMatchers("/api/auth/**").permitAll()

                // las demas rutas necesitan que el usuario este autenticado
                .anyRequest().authenticated())

            // configuramos la respuesta cuando falta el token o no es valido
            .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, e) -> {

                // enviamos el codigo 401 cuando el usuario no esta autorizado
                response.setStatus(401);

                // indicamos que la respuesta se enviara en formato json
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");

                // mensaje que se muestra cuando no se tiene un token valido
                response.getWriter().write(
                    "{\"status\":401,\"error\":\"no autorizado\",\"message\":\"falta el token o es invalido\"}");
            }))

            // agregamos nuestro filtro jwt antes del filtro de autenticacion de spring
            .addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        // regresamos la configuracion de seguridad
        return http.build();
    }

    // este metodo se encarga de encriptar las contraseñas con bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {

        // creamos y regresamos el codificador de contraseñas
        return new BCryptPasswordEncoder();
    }
}