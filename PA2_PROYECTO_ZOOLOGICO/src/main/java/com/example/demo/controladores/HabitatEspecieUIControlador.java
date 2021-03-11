/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */
@Controller
public class HabitatEspecieUIControlador {

    @Autowired
    private IndiceVulnerabilidadServicios servicioVulnerabilidad;
    
    @Autowired
    private EspecieServicios serviciosEspecie;
    
    @Autowired
    private HabitatsServicios serviciosHabitat;
    
    @RequestMapping("/habitat_especie")
    public String irMantenimiento(Model model) {
        setParametro(model, "lista_vulnerabilidad", servicioVulnerabilidad.getTodos());
        setParametro(model, "lista_especie", serviciosEspecie.getTodos());
        setParametro(model, "lista_habitat", serviciosHabitat.getTodos());
        return "paginas/form_habitat_especie";
    }
    
    
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

}
