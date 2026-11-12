package com.crudrapido.crudrapido.dto;

// importamos las validaciones que se utilizan para revisar los datos
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// datos que entran en post y put, con sus validaciones
public record StudentRequest(

        // el nombre es obligatorio y no debe pasar de 50 caracteres
        @NotBlank(message = "el nombre es obligatorio")
        @Size(max = 50, message = "el nombre no puede pasar de 50 caracteres")
        String firstName,

        // el apellido es obligatorio y tiene un limite de 50 caracteres
        @NotBlank(message = "el apellido es obligatorio")
        @Size(max = 50, message = "el apellido no puede pasar de 50 caracteres")
        String lastName,

        // el email es obligatorio y debe tener un formato correcto
        @NotBlank(message = "el email es obligatorio")
        @Email(message = "el email no tiene un formato valido")
        String email

) {} // aqui termina el record