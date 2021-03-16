/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Especies;
import com.example.demo.modelos.Vegetacion;
import com.example.demo.servicios.EspecieServicios;
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
 * @author Buddys
 */

@RestController
@RequestMapping(value = "/ws/especie")
public class EspecieApiControlador {
    
    @Autowired
    private EspecieServicios servicio;
    
    @GetMapping(value = "/")
    public List<Especies> getTodos() {
        return servicio.getTodos();
    }
    
    @GetMapping(value = "/{id}")
    public Optional<Especies> getValor(@PathVariable Long id) {
        return servicio.getValor(id);
    }
    
    @PostMapping(value="/guardar")
    public Especies guardar(@RequestBody Especies especie){
        return servicio.guardar(especie);
    }
    
    
    @GetMapping(value="/eliminar/{id}")
    public Optional<Especies> eliminar(@PathVariable Long id) {
        Optional<Especies> temp = getValor(id);
        servicio.eliminar(id);
        return temp;
    }
    
    
}
