package com.puertogames.puertogames_api.controller;
import com.puertogames.puertogames_api.model.Usuario;
import com.puertogames.puertogames_api.repository.UsuarioRepository;
import com.puertogames.puertogames_api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")

public class UsuarioController {
    private final UsuarioRepository repo;
    private final UsuarioService service;
    public UsuarioController(UsuarioRepository repo, UsuarioService service) {
        this.repo = repo;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Usuario usuario) {
        try {
            service.guardar(usuario); // assuming you have a UsuarioService
            return ResponseEntity.ok("Usuario registrado OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registro NO OK");
        }
    }
    //listar usuarios
    @GetMapping
    public List<Usuario> listar() {return repo.findAll();}

    //buscar usuario x id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarId(@PathVariable Long id){
        Usuario usuario = repo.findById(id).get();
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id,@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = repo.findById(id);
        if (usuario != null) {
            usuario.setId(id);
            usuario.setNombre(usuario.getNombre());
            usuario.setApellido(usuario.getApellido());
            usuario.setContrasena(usuario.getContrasena());
            usuario.setCorreo(usuario.getCorreo());
            return ResponseEntity.ok(repo.save(usuario));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar x id
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> eliminarId(@PathVariable Long id){
        if (id != null) {
            repo.deleteById(id);
            return ResponseEntity.ok("✅ Usuario eliminado");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar x correo
    @DeleteMapping("/correo/{correo}")
    public ResponseEntity<String> eliminarCorreo(@PathVariable String correo){
        Usuario usuario = repo.findByCorreo(correo).get();
        if (usuario != null) {
            repo.delete(usuario);
            return ResponseEntity.ok("✅ Usuario eliminado");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
