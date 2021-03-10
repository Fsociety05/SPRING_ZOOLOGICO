/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Habitats;
import com.example.demo.servicios.ClimaServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.VegetacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */
@Controller
public class HabitatsUIControlador {

    @Autowired
    private HabitatsServicios servicio;

    @Autowired
    private ClimaServicios servicioClima;
    
    @Autowired
    private VegetacionServicio servicioVegetacion;

    @RequestMapping("/irHabitats")
    public String irUsuario(Model model) {
        setParametro(model, "lista_Habitats", servicio.getTodos());

        return "paginas/mantenimiento_habitats";
    }

    @RequestMapping("/mantenimiento_habitats")
    public String irMantenimiento(Model model) {
        setParametro(model, "lista_Habitats", servicio.getTodos());
        return "paginas/mantenimiento_habitats";
    }
    
    @RequestMapping("/vista_habitats")
    public String vista(Model model) {
        setParametro(model, "listaHabitats", servicio.getTodos());
        return "paginas/vista_habitats";
    }

    @GetMapping("/crear_habitats")
    public String irCrear_usuario(Model model) {
        setParametro(model, "habitats", new Habitats());
        setParametro(model, "lista_clima", servicioClima.getTodos()); //se agregan los roles al combobox
        setParametro(model, "listaVegetaciones", servicioVegetacion.getTodos()); //se agregan los roles al combobox
        return "paginas/formHabitats";
    }

    @GetMapping("/actualizarHabitats/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {
        setParametro(modelo, "habitats", servicio.getValor(id));
        setParametro(modelo, "lista_clima", servicioClima.getTodos());
        setParametro(modelo, "listaVegetaciones", servicioVegetacion.getTodos()); //se agregan los roles al combobox
        return "paginas/formHabitats";
    }

    @PostMapping("/guardarHabitats")
    public String guardar(Habitats habitats, Model model) {
        servicio.guardar(habitats);
        return "redirect:/irHabitats"; 
    }

    @GetMapping("eliminarHabitats/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {
        servicio.eliminar(id);
        return "redirect:/irHabitats";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
