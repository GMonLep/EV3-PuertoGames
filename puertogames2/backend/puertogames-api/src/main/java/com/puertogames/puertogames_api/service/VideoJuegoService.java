package com.puertogames.puertogames_api.service;

import com.puertogames.puertogames_api.model.Videojuego;
import com.puertogames.puertogames_api.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoJuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    // Metodo para agregar un nuevo videojuego
    public Videojuego agregarVideojuego(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    // Metodo para obtener todos los videojuegos
    public List<Videojuego> obtenerTodos() {
        return videojuegoRepository.findAll();
    }

    // Metodo para obtener un videojuego por su ID
    public Videojuego obtenerPorId(Long id) {
        return videojuegoRepository.findById(id).orElse(null); // Devolver null si no lo encuentra
    }

    // Metodo para actualizar un videojuego
    public Videojuego actualizarVideojuego(Long id, Videojuego videojuego) {
        if (videojuegoRepository.existsById(id)) {
            videojuego.setId(id); // Mantener el mismo ID para la actualización
            return videojuegoRepository.save(videojuego);
        }
        return null; // Si no existe el videojuego con ese ID, retornar null
    }

    // Metodo para eliminar un videojuego
    public boolean eliminarVideojuego(Long id) {
        if (videojuegoRepository.existsById(id)) {
            videojuegoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
