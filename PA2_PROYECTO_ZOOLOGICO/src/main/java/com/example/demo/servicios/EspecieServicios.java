/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Especies;
import com.example.demo.repositorios.EspecieRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Buddys
 */
@Service
public class EspecieServicios {

    @Autowired
    private EspecieRepositorio repositorio;

    public Especies guardar(Especies entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Especies> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Especies> getTodos() {
        return (List<Especies>) repositorio.findAll();
    }
}
