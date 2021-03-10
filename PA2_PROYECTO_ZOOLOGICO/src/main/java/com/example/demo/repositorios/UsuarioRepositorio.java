/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.modelos.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Licona
 */
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long>{
    
}
