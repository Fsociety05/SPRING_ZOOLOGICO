/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.EspecieHabitat;
import com.example.demo.modelos.Usuario;
import com.example.demo.modelos.UsuarioLogueado;
import com.example.demo.servicios.EspecieHabitatServicios;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
import com.example.demo.servicios.UsuarioLogueadoServicios;
import com.example.demo.servicios.UsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    @Autowired
    private EspecieHabitatServicios servicio;
    
    @Autowired
    private UsuarioServicios serviciosUsuario;

    @Autowired
    private UsuarioLogueadoServicios serviciosUsuarioLogueado;
    
    
    @RequestMapping("/habitat_especie")
    public String irMantenimiento(Model model) {
        
        registrarUsuarioLogueado(model);
        setParametro(model, "lista_vulnerabilidad", servicioVulnerabilidad.getTodos());
        setParametro(model, "lista_especie", serviciosEspecie.getTodos());
        setParametro(model, "lista_habitat", serviciosHabitat.getTodos());
        
        setParametro(model, "objetoHabitadEspecie", new EspecieHabitat());
        
        return "paginas/form_habitat_especie";
    }
    
    @PostMapping("/guardar_EspecieHabitat")
    public String guardar(EspecieHabitat entidad, Model model, RedirectAttributes attribute) {
        
        if(entidad.getId_especie()==0){
            attribute.addFlashAttribute("error", "No hay registro de especie"); 
                return "redirect:/habitat_especie";
        }
        
        if(entidad.getId_habitat()==0){
            attribute.addFlashAttribute("error", "No hay registro de Habitat"); 
                return "redirect:/habitat_especie";
        }
        
        for (EspecieHabitat object : servicio.getPorHabitat(entidad.getId_habitat())) {
            if(object.getId_especie()==entidad.getId_especie()){
                attribute.addFlashAttribute("error", "Especie ya esta agragada al habitat"); 
                return "redirect:/habitat_especie";
            }
        }
        //System.out.println("Entro");
        servicio.guardar(entidad);

        //System.out.println(usuario.toString());
        attribute.addFlashAttribute("success", "Guardado correctamente"); //este es el mensage que quiero mostrar
        
        //System.out.println(entidad.toString());

        return "redirect:/habitat_especie";
    }
    
    
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
    
     public void registrarUsuarioLogueado(Model model) {
        Long id = null;
        for (Usuario todo : serviciosUsuario.getTodos()) {
            for (UsuarioLogueado object : serviciosUsuarioLogueado.getTodos()) {
                if(todo.getId()==object.getId()){
                    id = todo.getId();
                }
            }
        }

        setParametro(model, "registro", serviciosUsuario.getValor(id).get());
    }

}
