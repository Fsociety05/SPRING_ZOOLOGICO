/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.RolServicios;
import com.example.demo.servicios.UsuarioServicios;
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
 * @author Licona
 */
@Controller
public class UsuarioUIControlador {

    @Autowired
    private UsuarioServicios servicio;

    @Autowired
    private RolServicios servicioRol;

    @RequestMapping("/irUsuario")
    public String irUsuario(Model model) {
        setParametro(model, "lista_usuario", servicio.getTodos());

        return "paginas/mantenimiento_usuario";
    }

    @RequestMapping("/mantenimiento_usuario")
    public String irMantenimiento(Model model) {
        setParametro(model, "lista_usuario", servicio.getTodos());
        return "paginas/mantenimiento_usuario";
    }

    @GetMapping("/crear_usuario")
    public String irCrear_usuario(Model model) {
        setParametro(model, "registro", new Usuario());
        setParametro(model, "lista_rol", servicioRol.getTodos()); //se agregan los roles al combobox
        return "paginas/formUsuario";
    }

    @PostMapping("/guardar_usuario")
    public String guardar(Usuario usuario, Model model, RedirectAttributes attribute) {

        //System.out.println("Entro");
        servicio.guardar(usuario);

        //System.out.println(usuario.toString());
        attribute.addFlashAttribute("success", "Guardado correctamente"); //este es el mensage que quiero mostrar

        return "redirect:/mantenimiento_usuario";
    }

    @GetMapping("/actualizar_usuario/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        if (servicio.getValor(id).get().getId() == 1) {
            
            attribute.addFlashAttribute("error", "Este usuario no se puede editar");

        } else {
            setParametro(modelo, "registro", servicio.getValor(id));//se pone el ojeto de la pagina del formulario
            setParametro(modelo, "lista_rol", servicioRol.getTodos()); //se agregan los roles al combobox
            return "paginas/formUsuario";
        }
        // System.out.println("registro");
        return "redirect:/mantenimiento_usuario";
    }

    @GetMapping("eliminar_usuario/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        if (servicio.getValor(id).get().getId() == 1) {
            attribute.addFlashAttribute("error", "Este usuario no se puede eliminar");

        } else {
            servicio.eliminar(id);
            attribute.addFlashAttribute("success", "Eliminado correctamente");
        }

        return "redirect:/mantenimiento_usuario";

    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
