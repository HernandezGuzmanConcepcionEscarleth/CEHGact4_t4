package com.crudrapido.crudrapido.controller;

import com.crudrapido.crudrapido.dto.AuthResponse;
import com.crudrapido.crudrapido.dto.LoginRequest;
import com.crudrapido.crudrapido.dto.RegisterRequest;
import com.crudrapido.crudrapido.dto.UserResponse;
import com.crudrapido.crudrapido.entity.User;
import com.crudrapido.crudrapido.repository.UserRepository;
import com.crudrapido.crudrapido.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// esta clase se encarga de controlar el registro y el inicio de sesion
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // utilizamos el repositorio para guardar y buscar usuarios
    @Autowired
    private UserRepository userRepository;

    // utilizamos el codificador para proteger las contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    // utilizamos el servicio para generar los tokens jwt
    @Autowired
    private JwtService jwtService;

    // metodo para registrar un usuario y guardar su contraseña encriptada
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {

        // revisamos si el nombre de usuario ya existe
        if (userRepository.existsByUsername(req.username())) {

            // si ya existe, regresamos un error indicando que esta repetido
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", 409, "error", "el usuario ya existe"));
        }

        // revisamos si el correo ya esta registrado
        if (userRepository.existsByEmail(req.email())) {

            // si ya existe, regresamos un error indicando que el correo esta registrado
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", 409, "error", "el email ya esta registrado"));
        }

        // creamos un nuevo usuario para guardar sus datos
        User user = new User();

        // asignamos el nombre de usuario que se recibio
        user.setUsername(req.username());

        // asignamos el correo que se recibio
        user.setEmail(req.email());

        // encriptamos la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(req.password()));

        // guardamos el nuevo usuario en la base de datos
        User saved = userRepository.save(user);

        // respondemos con el codigo 201 y los datos del usuario sin mostrar la contraseña
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(saved));
    }

    // metodo para iniciar sesion y comprobar los datos del usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

        // buscamos al usuario por su nombre de usuario
        User user = userRepository.findByUsername(req.username()).orElse(null);

        // revisamos si el usuario existe y si la contraseña coincide
        if (user == null || !passwordEncoder.matches(req.password(), user.getPassword())) {

            // si los datos son incorrectos, regresamos un error de acceso no autorizado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", 401, "error", "credenciales invalidas"));
        }

        // generamos un token jwt con el nombre del usuario
        String token = jwtService.generateToken(user.getUsername());

        // regresamos el token para que el usuario pueda acceder a las rutas protegidas
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}