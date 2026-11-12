package com.crudrapido.crudrapido.dto;

// respuesta del login: el token jwt
public record AuthResponse(String token, String type) {}