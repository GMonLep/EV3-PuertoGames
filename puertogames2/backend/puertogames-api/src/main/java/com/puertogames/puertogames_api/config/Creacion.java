package com.puertogames.puertogames_api.config;
import com.puertogames.puertogames_api.model.Videojuego;
import com.puertogames.puertogames_api.repository.UsuarioRepository;
import com.puertogames.puertogames_api.model.Usuario;

import com.puertogames.puertogames_api.repository.VideojuegoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Creacion {
    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository) {
        return args -> {
            //iniciamos la cuenta vacia pq la base de datos va a estar vacia
            if (repository.count() == 0) {
                repository.save(Usuario.builder()
                        .nombre("Fulanito")
                        .apellido("Perez")
                        .contrasena("1234")
                        .correo("superperez@gmail.com")
                        .build());

                repository.save(Usuario.builder()
                        .nombre("Fulanita")
                        .apellido("Gonzales")
                        .contrasena("contra")
                        .correo("superfulanita@gmail.com")
                        .build());
                repository.save(Usuario.builder()
                        .nombre("Alberto")
                        .apellido("Albertini")
                        .contrasena("albertito")
                        .correo("ALBERTO@gmail.com")
                        .build());

                System.out.println("Usuarios creados");
            } else {
                System.out.println("Usuarios ya existen. No se agregaron");
            }
        };
    }
    @Bean
    CommandLineRunner initDatabase2(VideojuegoRepository repo) {
        return args -> {
            //iniciamos la cuenta vacia pq la base de datos va a estar vacia
            if (repo.count() == 0) {
                repo.save(Videojuego.builder()
                        .titulo("Super Mario Bros")
                        .genero("Diversion puzzles")
                        .plataforma("Nintendo")
                        .precio(30000)
                        .stock(9)
                        .build());

                repo.save(Videojuego.builder()
                        .titulo("Animal Crossing")
                        .genero("Cozy")
                        .plataforma("Nintendo")
                        .precio(20000)
                        .stock(20)
                        .build());

                repo.save(Videojuego.builder()
                        .titulo("DOOM: The Dark Ages")
                        .genero("Puro poder")
                        .plataforma("Play Station")
                        .precio(45000)
                        .stock(1)
                        .build());

                System.out.println("Juegos creados");
            } else {
                System.out.println("Juegos ya existen. No se agregaron");
            }
        };
    }


}
