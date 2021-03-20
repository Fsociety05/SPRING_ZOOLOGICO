/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.IndiceVulnerabilidad;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
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
@RequestMapping(value = "/ws/indice_vulnerabilidad")
public class VulnerabilidadApiControlador {
    
     @Autowired
    private IndiceVulnerabilidadServicios servicio;

    @GetMapping(value = "/")
    public List<IndiceVulnerabilidad> getTodos() {
        return servicio.getTodos();
    }
    
    @GetMapping(value = "/{id}")
    public Optional<IndiceVulnerabilidad> getValor(@PathVariable Long id) {
        return servicio.getValor(id);
    }
    
    @PostMapping(value="/guardar")
    public IndiceVulnerabilidad guardar(@RequestBody IndiceVulnerabilidad entidad){
        
        return servicio.guardar(entidad);
    }
    
    
    @GetMapping(value="/eliminar/{id}")
    public Optional<IndiceVulnerabilidad> eliminar(@PathVariable Long id) {
        Optional<IndiceVulnerabilidad> temp = getValor(id);
        servicio.eliminar(id);
        return temp;
    }
}
