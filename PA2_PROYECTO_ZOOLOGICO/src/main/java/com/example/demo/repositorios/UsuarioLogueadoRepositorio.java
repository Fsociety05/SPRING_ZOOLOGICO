/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.modelos.UsuarioLogueado;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Licona
 */
public interface UsuarioLogueadoRepositorio extends CrudRepository<UsuarioLogueado, Long>{
    
}
