package com.puertogames.puertogames_api.controller;
import com.puertogames.puertogames_api.model.Usuario;
import com.puertogames.puertogames_api.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")

public class UsuarioController {
    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {return repo.save(usuario);}

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
    @PutMapping
    public ResponseEntity<Usuario> editar(@PathVariable Long id,@RequestBody Usuario usuario) {
        if (usuario != null) {
            usuario.setId(id);
            usuario.setNombre(usuario.getNombre());
            usuario.setApellido(usuario.getApellido());
            usuario.setContrasena(usuario.getContrasena());
            usuario.setCorreo(usuario.getCorreo());
            usuario.setRol(usuario.getRol());
            return ResponseEntity.ok(repo.save(usuario));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar x id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarId(@PathVariable Long id){
        Usuario usuario = repo.findById(id).get();
        if (usuario != null) {
            repo.delete(usuario);
            return ResponseEntity.ok("✅ Usuario eliminado");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar x correo
    @DeleteMapping("/{correo}")
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
