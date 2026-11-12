package com.crudrapido.crudrapido.entity;

import jakarta.persistence.*;

// esta clase representa la tabla de usuarios en mysql
@Entity
@Table(name = "tbl_user")
public class User {

    // id que se genera automaticamente en mysql
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre de usuario, no puede estar vacio ni repetirse
    @Column(nullable = false, unique = true)
    private String username;

    // correo del usuario, es obligatorio y no se puede repetir
    @Column(nullable = false, unique = true)
    private String email;

    // aqui se guarda la contraseña encriptada con bcrypt, no la contraseña original
    @Column(nullable = false)
    private String password;

    // constructor vacio que necesita jpa para crear los objetos
    public User() {}

    // getter para obtener el id
    public Long getId() { return id; }

    // setter para asignar el id
    public void setId(Long id) { this.id = id; }

    // getter para obtener el nombre de usuario
    public String getUsername() { return username; }

    // setter para guardar el nombre de usuario
    public void setUsername(String username) { this.username = username; }

    // getter para obtener el correo
    public String getEmail() { return email; }

    // setter para guardar el correo
    public void setEmail(String email) { this.email = email; }

    // getter para obtener la contraseña guardada
    public String getPassword() { return password; }

    // setter para guardar la contraseña ya protegida
    public void setPassword(String password) { this.password = password; }
}