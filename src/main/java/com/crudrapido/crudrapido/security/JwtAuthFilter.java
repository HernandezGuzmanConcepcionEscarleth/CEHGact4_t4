package com.crudrapido.crudrapido.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// esta clase revisa el token en cada peticion antes de llegar al controller
public class JwtAuthFilter extends OncePerRequestFilter {

    // guardamos el servicio que se encarga de validar los tokens
    private final JwtService jwtService;

    // recibimos el servicio jwt para poder utilizarlo en esta clase
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // este metodo se ejecuta para revisar cada peticion que llega a la aplicacion
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // obtenemos el encabezado donde viene el token
        String header = request.getHeader("Authorization");

        // revisamos que el encabezado exista y comience con bearer
        if (header != null && header.startsWith("Bearer ")) {

            // quitamos la palabra bearer para obtener solamente el token
            String token = header.substring(7);

            // comprobamos si el token es valido
            if (jwtService.isValid(token)) {

                // obtenemos el nombre del usuario que viene dentro del token
                String username = jwtService.extractUsername(token);

                // creamos la autenticacion con los datos del usuario
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, List.of());

                // guardamos la autenticacion para que spring security reconozca al usuario
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // continuamos con la siguiente parte de la cadena de filtros
        chain.doFilter(request, response);
    }
}