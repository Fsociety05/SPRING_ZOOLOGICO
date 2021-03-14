/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Clima;
import com.example.demo.servicios.ClimaServicios;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Licona
 */
@RestController
@RequestMapping(value = "/ws/clima")
public class ClimaApiControlador {
    
    @Autowired
    private ClimaServicios servicio;
    
    @GetMapping(value = "/")
    public List<Clima> getTodos() {
        return servicio.getTodos();
    }
    
    @GetMapping(value = "/{id}")
    public Optional<Clima> getValor(@PathVariable Long id) {
        return servicio.getValor(id);
    }
    
    @PostMapping(value="/guardar")
    public Clima guardar(@RequestBody Clima entidad){
        
        return servicio.guardar(entidad);
    }
    
    
    @GetMapping(value="/eliminar/{id}")
    public Optional<Clima> eliminar(@PathVariable Long id) {
        Optional<Clima> temp = getValor(id);
        servicio.eliminar(id);
        return temp;
    }
    
    
}

