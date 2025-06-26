

package com.puertogames.puertogames_api.controller;

import com.puertogames.puertogames_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String contrasena = loginData.get("contrasena");

        if (correo == null || contrasena == null) {
            return ResponseEntity.badRequest().body("Faltan datos");
        }

        boolean valido = usuarioService.validarUsuario(correo, contrasena);
        if (valido) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }
}
