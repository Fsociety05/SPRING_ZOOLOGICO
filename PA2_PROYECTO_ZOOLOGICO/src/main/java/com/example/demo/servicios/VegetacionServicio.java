/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Vegetacion;
import com.example.demo.repositorios.VegetacionRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Buddys
 */
@Service
public class VegetacionServicio {
    
    @Autowired
    private VegetacionRepositorio repositorio;

    public Vegetacion guardar(Vegetacion entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Vegetacion> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Vegetacion> getTodos() {
        return (List<Vegetacion>) repositorio.findAll();
    }
}
