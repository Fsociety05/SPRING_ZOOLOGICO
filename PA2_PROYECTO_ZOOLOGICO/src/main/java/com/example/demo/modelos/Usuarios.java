package com.example.demo.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Usuarios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    private int rol;
}
