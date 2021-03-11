/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Habitats;
import com.example.demo.modelos.Itinerario;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.ItinerarioServicios;
import java.util.List;
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
public class ItinerarioUIControlador {

    @Autowired
    private ItinerarioServicios servicio;

    @Autowired
    private HabitatsServicios servicioHabitat;

    @RequestMapping("/mantenimiento_itinerario")
    public String irMantenimiento(Model model) {
        verificacion();
        setParametro(model, "lista_Itinerario", servicio.getTodos());
        return "paginas/mantenimiento_itinerario";
    }

    @RequestMapping("/vista_itinerario")
    public String vista(Model model) {
        verificacion();
        setParametro(model, "lista_Itinerario", servicio.getTodos());
        return "paginas/vista_Itinerario";
    }
    
    private void verificacion() {
        Itinerario arreglo[] = new Itinerario[servicio.getTodos().size()];
        int i = 0;
        for (Itinerario itinerario : servicio.getTodos()) {
            arreglo[i] = itinerario;
            i++;
        }

        for (int j = 0; j < arreglo.length; j++) {
            //arreglo[j].setNombreHabitat(servicioHabitat.getUno(arreglo[j].getId_habitat()).getNombre());
            
            arreglo[j].setNombreHabitat("Hols");
        }
    }

    @GetMapping("/crear_itinerario")
    public String irCrear_itinerario(Model model) {
        setParametro(model, "itinerario", new Itinerario());
        setParametro(model, "lista_habitat_combo", servicioHabitat.getTodos());
        return "paginas/formItinerario";
    }

    @GetMapping("/actualizarItinerario/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {
        setParametro(modelo, "itinerario", servicio.getValor(id));
        setParametro(modelo, "lista_habitat_combo", servicioHabitat.getTodos());
        return "paginas/formItinerario";
    }

    @PostMapping("/guardarItinerario")
    public String guardar(Itinerario itinerario, Model model) {
        servicio.guardar(itinerario);
        return "redirect:/mantenimiento_itinerario";
    }

    @GetMapping("eliminarItinerario/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {
        servicio.eliminar(id);
        return "redirect:/mantenimiento_itinerario";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

}
