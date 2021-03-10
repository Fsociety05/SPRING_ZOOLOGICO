/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Rol;
import com.example.demo.repositorios.RolRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author Licona
 */

@Service
public class RolServicios {
    
    @Autowired
    private RolRepositorio repositorio;
    
    public Rol guardar(Rol entidad){
        
        return repositorio.save(entidad);
    }
    
     public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Rol> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Rol> getTodos() {
        return (List<Rol>) repositorio.findAll();
    }
}
