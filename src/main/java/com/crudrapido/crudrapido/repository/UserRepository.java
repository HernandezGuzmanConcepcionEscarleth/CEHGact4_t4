package com.crudrapido.crudrapido.repository;


import com.crudrapido.crudrapido.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// esta interfaz permite realizar operaciones con la tabla de usuarios
public interface UserRepository extends JpaRepository<User, Long> {

    // busca un usuario por su nombre de usuario para iniciar sesion
    Optional<User> findByUsername(String username);

    // revisa si ya existe un nombre de usuario para evitar que se repita
    boolean existsByUsername(String username);

    // revisa si ya existe un correo para evitar registros duplicados
    boolean existsByEmail(String email);
}