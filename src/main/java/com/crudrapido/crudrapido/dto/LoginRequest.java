package com.crudrapido.crudrapido.dto;

import jakarta.validation.constraints.NotBlank;

// datos que entran al hacer login
public record LoginRequest(

        // el nombre de usuario es obligatorio para poder iniciar sesion
        @NotBlank(message = "el usuario es obligatorio")
        String username,

        // la contraseña tambien es obligatoria para iniciar sesion
        @NotBlank(message = "la contraseña es obligatoria")
        String password

) {} 