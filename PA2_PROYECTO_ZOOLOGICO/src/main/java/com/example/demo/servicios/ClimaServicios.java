/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Clima;
import com.example.demo.modelos.Rol;
import com.example.demo.repositorios.ClimaRepositorio;
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
public class ClimaServicios {
    
    @Autowired
    private ClimaRepositorio repositorio;
    
    public Clima guardar(Clima entidad){
        
        return repositorio.save(entidad);
    }
    
     public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Clima> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Clima> getTodos() {
        return (List<Clima>) repositorio.findAll();
    }
}
