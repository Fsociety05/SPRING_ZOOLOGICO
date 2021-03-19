/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Licona
 */
@Data
@NoArgsConstructor
@Entity
public class UsuarioLogueado implements Serializable{
    @Id
    private Long id;
    private String usuario;
    private Long id_rol;
}
