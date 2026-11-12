package com.crudrapido.crudrapido.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// datos que entran al registrarse
public record RegisterRequest(

        // el nombre de usuario es obligatorio y debe tener entre 3 y 50 caracteres
        @NotBlank(message = "el usuario es obligatorio")
        @Size(min = 3, max = 50, message = "el usuario debe tener entre 3 y 50 caracteres")
        String username,

        // el email es obligatorio y debe tener un formato valido
        @NotBlank(message = "el email es obligatorio")
        @Email(message = "el email no tiene un formato valido")
        String email,

        // la contraseña es obligatoria y debe tener minimo 6 caracteres
        @NotBlank(message = "la contraseña es obligatoria")
        @Size(min = 6, max = 100, message = "la contraseña debe tener minimo 6 caracteres")
        String password

) {} 