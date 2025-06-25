package com.puertogames.puertogames_api.controller;
import com.puertogames.puertogames_api.controller.VideojuegoController;
import com.puertogames.puertogames_api.model.Usuario;
import com.puertogames.puertogames_api.repository.UsuarioRepository;

//importamos junit para testeo jiji
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//esta es para q solo tome el controlador para pruebas
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//simulacion de bean
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

//Inyección automática del cliente de pruebas web wtm
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

//metodos estaticos de mockito
import static org.mockito.Mockito.*;

//6 Métodos para construir peticiones HTTP simuladas y verificar respuestas
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

//7 Librería para convertir objetos a JSON (necesaria en peticiones POST)
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;
import java.util.Optional;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioRepository repo;

    //Convertir de texto a JSON y viceversa
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("TEST 1: POST")
    void testPost() throws Exception {
        Usuario usuario = new Usuario(null,"Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador");

        when(repo.save(any())).thenReturn(new Usuario(1L,"Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador"));
        mockMvc.perform(post("/api/usuarios")
                        .contentType("application/json")//indicar que el contenido es JSON
                        .content(mapper.writeValueAsString(usuario)))// Convertimos el objeto JSON
                .andExpect(status().isOk()) //200
                .andExpect(jsonPath("$.nombre").value("Juanito"));

    }

    @Test
    @DisplayName("TESTING 2 GET LISTA")
    void testGetAll() throws Exception{
        when(repo.findAll()).thenReturn(List.of(new Usuario(1L, "Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador")));
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juanito"));
    }

    @Test
    @DisplayName("TESTING 3 GET POR ID")
    void testGetById() throws Exception{

        Usuario usuario = new Usuario(1L,"Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador");

        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juanito"));
    }

    @Test
    @DisplayName("TESTING 4 PUT")
    void testPut() throws Exception{
        Usuario original = new Usuario (1L,"Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador");
        Usuario actualizado = new Usuario();
        actualizado.setApellido("De las Mancillas");
        actualizado.setContrasena("contrasenia2");


        when(repo.findById(1L)).thenReturn(Optional.of(original));
        when(repo.save(any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType("application/json")
                .content(mapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("De las Mancillas"))
                .andExpect(jsonPath("$.contrasena").value("contrasenia2"));
    }

    @Test
    @DisplayName("TEST 5 DELETE POR ID")
    void testDeleteById() throws Exception{
        doNothing().when(repo).deleteById(1L);
        mockMvc.perform(delete("/api/usuarios/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TEST 5 DELETE POR CORREO")
    void testDeleteByCorreo() throws Exception{
        Usuario usuario = new Usuario (1L,"Juanito","Perez","contrasenia1","juanit@gmail.com","Administrador");
        when(repo.findByCorreo("juanit@gmail.com")).thenReturn(Optional.of(usuario));
        doNothing().when(repo).delete(usuario);
        mockMvc.perform(delete("/api/usuarios/correo/juanit@gmail.com"))
                .andExpect(status().isOk());
    }
}


