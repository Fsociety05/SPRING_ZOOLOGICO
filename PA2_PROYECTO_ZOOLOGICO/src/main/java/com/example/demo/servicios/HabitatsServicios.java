/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Habitats;
import com.example.demo.repositorios.HabitatsRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class HabitatsServicios {
    
    @Autowired
    private HabitatsRepositorio repositorio;
    
    public Habitats guardar(Habitats entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Habitats> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Habitats> getTodos() {
        return (List<Habitats>) repositorio.findAll();
    }
    
    public Habitats getUno(Long id) {
        return repositorio.findById(id).get();
    }
}
