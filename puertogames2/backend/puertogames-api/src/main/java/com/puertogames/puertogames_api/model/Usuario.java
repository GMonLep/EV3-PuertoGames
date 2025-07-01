package com.puertogames.puertogames_api.model;
import lombok.*;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
}
