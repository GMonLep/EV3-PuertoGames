package com.puertogames.puertogames_api.controller;

import com.puertogames.puertogames_api.model.Videojuego;
import com.puertogames.puertogames_api.service.VideoJuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
@CrossOrigin("*") // Para que tu API reciba peticiones desde cualquier parte
public class VideojuegoController {

    private final VideoJuegoService videojuegoService;

    public VideojuegoController(VideoJuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    // Endpoint para listar todos los videojuegos
    @GetMapping
    public List<Videojuego> listar() {
        return videojuegoService.obtenerTodos();
    }

    // Endpoint para crear un nuevo videojuego
    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego) {
        System.out.println("Post exitoso " + videojuego);
        return videojuegoService.agregarVideojuego(videojuego);
    }

    // Endpoint para actualizar un videojuego existente
    @PutMapping("/{id}")
    public Videojuego actualizar(@PathVariable Long id, @RequestBody Videojuego videojuego) {
        return videojuegoService.actualizarVideojuego(id, videojuego);
    }

    // Endpoint para eliminar un videojuego
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        videojuegoService.eliminarVideojuego(id);
    }
}

