/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Vegetacion;
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
 * @author Buddys
 */
@Controller
public class VegetacionUIControlador {
   @Autowired
    private VegetacionServicio servicio;   
   
   @RequestMapping("/irVegetacion")
   public String irMantenimiento(Model model) { 
        setParametro(model, "listaVegetacion", servicio.getTodos());
        return "paginas/mantenimiento_vegetacion";
    }
   
   @GetMapping("/crearVegetacion")
   public String irCrear(Model model) {
       setParametro(model, "vegetacion", new Vegetacion());
       return "paginas/formVegetacion";
   }
   
   public void setParametro(Model model, String atributo, Object valor){
        model.addAttribute(atributo, valor);
   }
   
   @GetMapping("/actualizarVegetacion/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {
        setParametro(modelo, "vegetacion", servicio.getValor(id));
        return "paginas/formVegetacion";
    }

    @PostMapping("/guardarVegetacion")
    public String guardar(Vegetacion vegetacion, Model model) {
        servicio.guardar(vegetacion);
        return "redirect:/irVegetacion"; 
    }

    @GetMapping("eliminarVegetacion/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {
        servicio.eliminar(id);
        return "redirect:/irVegetacion";
    }
}
