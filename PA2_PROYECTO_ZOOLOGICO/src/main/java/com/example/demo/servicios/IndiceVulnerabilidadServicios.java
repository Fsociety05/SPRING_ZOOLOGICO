/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.IndiceVulnerabilidad;
import com.example.demo.modelos.Rol;
import com.example.demo.repositorios.IndiceVulnerabilidadRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class IndiceVulnerabilidadServicios {
    
    @Autowired
    private IndiceVulnerabilidadRepositorio repositorio;
    
    public IndiceVulnerabilidad guardar(IndiceVulnerabilidad entidad){
        
        return repositorio.save(entidad);
    }
    
     public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<IndiceVulnerabilidad> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<IndiceVulnerabilidad> getTodos() {
        return (List<IndiceVulnerabilidad>) repositorio.findAll();
    }
}
