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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Buddys
 */
@Controller
public class ItinerarioUIControlador {

    private boolean editando = false;

    @Autowired
    private ItinerarioServicios servicio;

    @Autowired
    private HabitatsServicios servicioHabitat;

    @RequestMapping("/mantenimiento_itinerario" )
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        //verificacion();
        setParametro(model, "lista_Itinerario", servicio.getTodos());
        return "paginas/mantenimiento_itinerario";
    }

    @RequestMapping("/vista_itinerario")
    public String vista(Model model) {
        //verificacion();
        setParametro(model, "lista_Itinerario", servicio.getTodos());
        return "paginas/vista_Itinerario";
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

        editando = true;

        return "paginas/formItinerario";
    }

    @PostMapping("/guardarItinerario")
    public String guardar(Itinerario itinerario, Model model, RedirectAttributes attribute) {

        List<Itinerario> temp = servicio.getTodos();

        if (itinerario.getId_habitat() == 0) {
            attribute.addFlashAttribute("error", "No hay habitats en el sistema");
            return "redirect:/crear_itinerario";
        }
        
        System.out.println("Editando= " + editando);
        
        for (Itinerario todo : temp) {

            if (editando) {
                if (todo.getId_habitat() == itinerario.getId_habitat()) {

                    if (todo.getId() == itinerario.getId()) {

                    } else {
                        editando = false;
                        attribute.addFlashAttribute("error", "El habitat ya esta registrado en un itinerario");
                        return "redirect:/mantenimiento_itinerario";
                    }
                }

                if (itinerario.getNombre().equals(todo.getNombre())) {

                    if (todo.getId() == itinerario.getId()) {

                    } else {
                        editando = false;
                        attribute.addFlashAttribute("error", "El nombre del itinerario ya esta registrado");
                        return "redirect:/mantenimiento_itinerario";
                    }

                }

            } else {
                if (todo.getId_habitat() == itinerario.getId_habitat()) {
                    attribute.addFlashAttribute("error", "El habitat ya esta registrado en un itinerario");
                    return "redirect:/crear_itinerario";
                }

                if (itinerario.getNombre().equals(todo.getNombre())) {
                    attribute.addFlashAttribute("error", "El nombre del itinerario ya esta registrado");
                    return "redirect:/crear_itinerario";
                }
            }

        }
        editando = false;

        itinerario.setNombreHabitat(servicioHabitat.getValor(itinerario.getId_habitat()).get().getNombre());

        servicio.guardar(itinerario);

        attribute.addFlashAttribute("success", "Guardado correctamente");
        return "redirect:/crear_itinerario";
    }

    @GetMapping("eliminarItinerario/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {
        servicio.eliminar(id);
        attribute.addFlashAttribute("success", "Eliminado correctamente");
        return "redirect:/mantenimiento_itinerario";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

}
