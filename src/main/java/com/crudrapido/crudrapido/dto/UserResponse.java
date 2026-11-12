package com.crudrapido.crudrapido.dto;

import com.crudrapido.crudrapido.entity.User;

// respuesta del registro: sin password, nunca se expone
public record UserResponse(Long id, String username, String email) {

    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail());
    }
}