/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Usuario;
import com.example.demo.modelos.UsuarioLogueado;
import com.example.demo.modelos.Vegetacion;
import com.example.demo.servicios.UsuarioLogueadoServicios;
import com.example.demo.servicios.UsuarioServicios;
import com.example.demo.servicios.VegetacionServicio;
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
public class VegetacionUIControlador {

    private boolean editando = false;

    @Autowired
    private VegetacionServicio servicio;

    @Autowired
    private UsuarioServicios serviciosUsuario;

    @Autowired
    private UsuarioLogueadoServicios serviciosUsuarioLogueado;

    @RequestMapping("/irVegetacion")
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        
        registrarUsuarioLogueado(model);
        setParametro(model, "listaVegetacion", servicio.getTodos());
        return "paginas/mantenimiento_vegetacion";
    }

    @GetMapping("/crearVegetacion")
    public String irCrear(Model model) {
        registrarUsuarioLogueado(model);
        setParametro(model, "vegetacion", new Vegetacion());
        return "paginas/formVegetacion";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

    @GetMapping("/actualizarVegetacion/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {
        editando = true;
        registrarUsuarioLogueado(modelo);
        setParametro(modelo, "vegetacion", servicio.getValor(id));
        return "paginas/formVegetacion";
    }

    @PostMapping("/guardarVegetacion")
    public String guardar(Vegetacion vegetacion, Model model, RedirectAttributes attribute) {

        for (Vegetacion todo : servicio.getTodos()) {

            if (editando) {
                if (todo.getId() == vegetacion.getId()) {

                } else {
                    if (todo.getNombre().equals(vegetacion.getNombre())) {
                        editando = false;
                        attribute.addFlashAttribute("error", "El nombre de la vegetacion ya existe");
                        return "redirect:/irVegetacion";
                    }
                }
            } else {
                if (todo.getNombre().equals(vegetacion.getNombre())) {
                    attribute.addFlashAttribute("success", "El nombre de la vegetacion ya existe");
                    return "redirect:/crearVegetacion";
                }
            }

        }

        editando = false;

        attribute.addFlashAttribute("success", "Guardado Correctamente");
        servicio.guardar(vegetacion);
        return "redirect:/crearVegetacion";
    }

    @GetMapping("eliminarVegetacion/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {
        servicio.eliminar(id);
        attribute.addFlashAttribute("success", "Eliminado correctamente");
        return "redirect:/irVegetacion";
    }

    public void registrarUsuarioLogueado(Model model) {
        Long id = null;
        for (Usuario todo : serviciosUsuario.getTodos()) {
            for (UsuarioLogueado object : serviciosUsuarioLogueado.getTodos()) {
                if (todo.getId() == object.getId()) {
                    id = todo.getId();
                }
            }
        }

        setParametro(model, "registro", serviciosUsuario.getValor(id).get());
    }
}
