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

    private boolean editando = false;
    private Usuario UsuarioLogueado = null;

    @Autowired
    private UsuarioServicios servicio;

    @Autowired
    private RolServicios servicioRol;

    @RequestMapping("/irUsuario")
    public String irUsuario(Model model, RedirectAttributes attribute) {
        setParametro(model, "lista_usuario", servicio.getTodos());

        return "paginas/mantenimiento_usuario";
    }

    @RequestMapping("/mantenimiento_usuario")
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        setParametro(model, "lista_usuario", servicio.getTodos());
        return "paginas/mantenimiento_usuario";
    }

    @GetMapping("/crear_usuario")
    public String irCrear_usuario(Model model, RedirectAttributes attribute) {
        setParametro(model, "registro", new Usuario());
        setParametro(model, "lista_rol", servicioRol.getTodos()); //se agregan los roles al combobox
        return "paginas/formUsuario";
    }

    @PostMapping("/guardar_usuario")
    public String guardar(Usuario usuario, Model model, RedirectAttributes attribute) {

        for (Usuario todo : servicio.getTodos()) {
            if (editando) {
                if (todo.getId() == usuario.getId()) {

                } else {
                    if (!todo.getNom_usuario().equalsIgnoreCase("admin")) {
                        if (todo.getNom_usuario().equals(usuario.getNom_usuario())) {
                            editando = false;
                            attribute.addFlashAttribute("error", "El usuario no esta disponible kkkkk");
                            return "redirect:/mantenimiento_usuario";
                        }

                        if (todo.getDni().equals(usuario.getDni())) {
                            editando = false;
                            attribute.addFlashAttribute("error", "El DNI ya esta en otro registro");
                            return "redirect:/mantenimiento_usuario";
                        }

                        if (todo.getTelefono().equals(usuario.getTelefono())) {
                            editando = false;
                            attribute.addFlashAttribute("error", "El Telefono ya esta en otro registro");
                            return "redirect:/mantenimiento_usuario";
                        }

                        if (todo.getCorreo().equals(usuario.getCorreo())) {
                            editando = false;
                            attribute.addFlashAttribute("error", "El Correo ya esta en otro registro");
                            return "redirect:/mantenimiento_usuario";
                        }
                    }

                }
            } else {

                if (todo.getNom_usuario().equals(usuario.getNom_usuario())) {
                    attribute.addFlashAttribute("error", "El usuario no esta disponible");
                    return "redirect:/crear_usuario";
                }

                if (!todo.getNom_usuario().equalsIgnoreCase("admin")) {
                    if (todo.getDni().equals(usuario.getDni())) {
                        attribute.addFlashAttribute("error", "El DNI ya esta en otro registro");
                        return "redirect:/crear_usuario";
                    }

                    if (todo.getTelefono().equals(usuario.getTelefono())) {
                        attribute.addFlashAttribute("error", "El Telefono ya esta en otro registro");
                        return "redirect:/crear_usuario";
                    }

                    if (todo.getCorreo().equals(usuario.getCorreo())) {
                        attribute.addFlashAttribute("error", "El Correo ya esta en otro registro");
                        return "redirect:/crear_usuario";
                    }
                }

            }
        }

        editando = false;
        attribute.addFlashAttribute("success", "Guardado correctamente"); //este es el mensage que quiero mostrar
        servicio.guardar(usuario);
        return "redirect:/crear_usuario";

    }

    @GetMapping("/actualizar_usuario/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        if (servicio.getValor(id).get().getId() == 1) {

            attribute.addFlashAttribute("error", "Este usuario no se puede editar");

        } else {
            editando = true;
            setParametro(modelo, "registro", servicio.getValor(id));//se pone el ojeto de la pagina del formulario
            setParametro(modelo, "lista_rol", servicioRol.getTodos()); //se agregan los roles al combobox
            return "paginas/formUsuario";
        }

        editando = true;
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
    
    public Usuario getParametroUsuario(Model model, String atributo) {
        Usuario temp = (Usuario) model.getAttribute(atributo);
        return temp;
    }
    
    
    public Usuario getUsuarioLogueado(){
        return UsuarioLogueado;
    }
    
    public void setUsuarioLogueado(Usuario user){
        UsuarioLogueado = user;
    }
}
