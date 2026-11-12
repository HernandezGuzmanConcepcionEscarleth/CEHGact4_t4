package com.crudrapido.crudrapido.dto;

// importamos la clase student para utilizar sus datos

import com.crudrapido.crudrapido.entity.Student;

// datos que salen en las respuestas, sin exponer directamente la entity
public record StudentResponse(
        Long studentId,
        String firstName,
        String lastName,
        String email
) {

    // este metodo convierte los datos de student en un dto de respuesta
    public static StudentResponse from(Student s) {

        // obtenemos los datos del estudiante y los regresamos en studentresponse
        return new StudentResponse(
                s.getStudentId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail()
        );
    }
}