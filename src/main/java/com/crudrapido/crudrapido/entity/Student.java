package com.crudrapido.crudrapido.entity;

// importamos las anotaciones para trabajar con la base de datos
import jakarta.persistence.*;

// esta clase representa la tabla de estudiantes en mysql
@Entity
@Table(name = "tbl_student")
public class Student {

    // id que se genera automaticamente en mysql
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    // datos que va a guardar cada estudiante
    private String firstName;
    private String lastName;
    private String email;

    // constructor vacio que necesita jpa para crear los objetos
    public Student() {}

    // getter para obtener el id del estudiante
    public Long getStudentId() { return studentId; }

    // setter para asignar el id del estudiante
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    // getter para obtener el nombre
    public String getFirstName() { return firstName; }

    // setter para guardar el nombre
    public void setFirstName(String firstName) { this.firstName = firstName; }

    // getter para obtener el apellido
    public String getLastName() { return lastName; }

    // setter para guardar el apellido
    public void setLastName(String lastName) { this.lastName = lastName; }

    // getter para obtener el correo
    public String getEmail() { return email; }

    // setter para guardar el correo
    public void setEmail(String email) { this.email = email; }
}