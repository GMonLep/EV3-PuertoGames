package com.puertogames.puertogames_api.controller;

import com.puertogames.puertogames_api.model.Videojuego;
import com.puertogames.puertogames_api.service.VideoJuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/stock")
    public List<Map<String, Object>> getCategoriaStock() {
        List<Videojuego> videojuegos = videojuegoService.obtenerTodos();

        return videojuegos.stream()
                .collect(Collectors.groupingBy(
                        Videojuego::getGenero,
                        Collectors.summingInt(Videojuego::getStock)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("categoria", entry.getKey());
                    map.put("stock", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/plataforma")
    public List<Map<String, Object>> getPlatformaStock() {
        List<Videojuego> videojuegos = videojuegoService.obtenerTodos();

        return videojuegos.stream()
                .collect(Collectors.groupingBy(
                        Videojuego::getPlataforma,
                        Collectors.summingInt(Videojuego::getStock)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("plataforma", entry.getKey());
                    map.put("stock", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/juego")
    public List<Map<String, Object>> getStockJuego() {
        List<Videojuego> videojuegos = videojuegoService.obtenerTodos();

        return videojuegos.stream()
                .collect(Collectors.groupingBy(
                        Videojuego::getTitulo,
                        Collectors.summingInt(Videojuego::getStock)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("titulo", entry.getKey());
                    map.put("stock", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/precio")
    public List<Map<String, Object>> getPrecioPlataforma() {
        List<Videojuego> videojuegos = videojuegoService.obtenerTodos();

        return videojuegos.stream()
                .collect(Collectors.groupingBy(
                        Videojuego::getPlataforma,
                        Collectors.summingDouble(Videojuego::getPrecio)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("plataforma", entry.getKey());
                    map.put("precio", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
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

    @GetMapping("/rawg/buscar")
    @CrossOrigin(origins = "*")
    public String buscarJuego(@RequestParam String nombre) {
    String apiKey = "5c9b0cea670f41499bf38f1eaded59cf";
    String url = "https://api.rawg.io/api/games?key=" + apiKey + "&search=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);
    
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(url, String.class);
    }


}

