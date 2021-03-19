/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Itinerario;
import com.example.demo.repositorios.ItinerarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class ItinerarioServicios {
    
    @Autowired
    private ItinerarioRepositorio repositorio;
    
    public Itinerario guardar(Itinerario entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Itinerario> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Itinerario> getTodos() {
        return (List<Itinerario>) repositorio.findAll();
    }
    
}
