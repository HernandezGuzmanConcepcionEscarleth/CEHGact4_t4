package com.crudrapido.crudrapido.dto;

// Importamos las validaciones que vamos a utilizar
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Este record sirve para recibir los datos de un estudiante
public record StudentRequest(

        // El nombre no debe estar vacío y puede tener máximo 50 caracteres
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede pasar de 50 caracteres")
        String firstName,

        // El apellido también es obligatorio y tiene un límite de 50 caracteres
        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no puede pasar de 50 caracteres")
        String lastName,

        // El correo es obligatorio y debe tener un formato de email válido
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email

) {} 