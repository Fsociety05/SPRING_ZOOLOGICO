/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.UsuarioLogueado;
import com.example.demo.repositorios.UsuarioLogueadoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */

@Service
public class UsuarioLogueadoServicios {
    @Autowired
    private UsuarioLogueadoRepositorio repositorio;
    
    public UsuarioLogueado guardar(UsuarioLogueado entidad){
        
        return repositorio.save(entidad);
    }
    
     public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<UsuarioLogueado> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<UsuarioLogueado> getTodos() {
        return (List<UsuarioLogueado>) repositorio.findAll();
    }
}
