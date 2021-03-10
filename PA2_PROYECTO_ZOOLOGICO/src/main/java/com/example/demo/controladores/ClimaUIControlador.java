/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Rol;
import com.example.demo.servicios.ClimaServicios;
import com.example.demo.servicios.RolServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */

@Controller
public class ClimaUIControlador {
    
    @Autowired
    private ClimaServicios servicio;
    
    public void setParametro(Model model, String atributo, Object valor){
        model.addAttribute(atributo, valor);
    }
}
