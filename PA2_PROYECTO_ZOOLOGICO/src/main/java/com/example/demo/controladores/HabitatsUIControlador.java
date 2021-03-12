/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.EspecieHabitat;
import com.example.demo.modelos.Especies;
import com.example.demo.modelos.Habitats;
import com.example.demo.modelos.IndiceVulnerabilidad;
import com.example.demo.servicios.ClimaServicios;
import com.example.demo.servicios.EspecieHabitatServicios;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
import com.example.demo.servicios.VegetacionServicio;
import java.util.ArrayList;
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
public class HabitatsUIControlador {

    @Autowired
    private HabitatsServicios servicio;
    
    @Autowired
    private EspecieServicios servicioEspecie;
    
    @Autowired
    private IndiceVulnerabilidadServicios servicioVulneravilidad;
    
    @Autowired
    private EspecieHabitatServicios servicioEspecieHabitat;

    @Autowired
    private ClimaServicios servicioClima;

    @Autowired
    private VegetacionServicio servicioVegetacion;

    @RequestMapping("/mantenimiento_habitats")
    public String irMantenimiento(Model model) {
        
//        Habitats[] arreglo = new Habitats[servicio.getTodos().size()];
//        int i = 0;
//        for (Habitats habitats : servicio.getTodos()) {
//            arreglo[i] = habitats;
//            i++;
//        }
//        
//        for (int j = 0; j < arreglo.length; j++) {
//            arreglo[j].setNombreVegetacion(servicioVegetacion.getUno(arreglo[j].getId_vegetacion()).getNombre()+"");
//        }
        
        setParametro(model, "lista_Habitats", servicio.getTodos());
        return "paginas/mantenimiento_habitats";
    }

    @RequestMapping("/vista_habitats")
    public String vista(Model model) {
        setParametro(model, "listaHabitats", servicio.getTodos());
        return "paginas/vista_habitats";
    }
    
    @GetMapping("/eliminarEspecieHabitats/{id}/{id_especie}")
    public String vistaEliminarEspecie(@PathVariable("id") Long id_habitat,@PathVariable("id_especie") Long id_especie, Model modelo) {
        //setParametro(model, "listaHabitats", servicio.getTodos());
        
        //System.out.println("Id especie="+id_especie+" id habitat="+id_habitat);
        
        List<Especies> tempEspecie = new ArrayList<>();
       // List<IndiceVulnerabilidad> tempVulneravilidad = servicioVulneravilidad.getTodos();
        
       
        
        for (EspecieHabitat especieHabitat : servicioEspecieHabitat.getPorHabitat(id_habitat)) {
            
            if(especieHabitat.getId_especie()==id_especie){
                servicioEspecieHabitat.eliminar(especieHabitat.getId());
            }else{
                tempEspecie.add(servicioEspecie.getValor(especieHabitat.getId_especie()).get());
            }
            
        }
        
        setParametro(modelo, "listaEspecies", tempEspecie);
        setParametro(modelo, "listaVulnerabilidad", servicioVulneravilidad.getTodos());
        setParametro(modelo, "listaEspecieHabitat", servicioEspecieHabitat.getPorHabitat(id_habitat)); //se agregan los roles al combobox
        
        
        return "paginas/ver_especie_habitat";
    }
    
    @GetMapping("/verEspecieHabitats/{id}")
    public String verEspeciesEnHabitat(@PathVariable("id") Long id, Model modelo) {
        
        List<Especies> tempEspecie = new ArrayList<>();
       // List<IndiceVulnerabilidad> tempVulneravilidad = servicioVulneravilidad.getTodos();
        
        
        for (EspecieHabitat especieHabitat : servicioEspecieHabitat.getPorHabitat(id)) {
            tempEspecie.add(servicioEspecie.getValor(especieHabitat.getId_especie()).get());
        }
        
        setParametro(modelo, "listaEspecies", tempEspecie);
        setParametro(modelo, "listaVulnerabilidad", servicioVulneravilidad.getTodos());
        setParametro(modelo, "listaEspecieHabitat", servicioEspecieHabitat.getPorHabitat(id)); //se agregan los roles al combobox
        
        System.out.println(id);
        return "paginas/ver_especie_habitat";
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
        
        habitats.setNombreVegetacion(servicioVegetacion.getValor(habitats.getId_vegetacion()).get().getNombre());
        
        servicio.guardar(habitats);
        return "redirect:/mantenimiento_habitats";
    }

    @GetMapping("eliminarHabitats/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {
        servicio.eliminar(id);
        return "redirect:/mantenimiento_habitats";
    }

    public void setParametro(Model model, String atributo, Object valor) { 
        model.addAttribute(atributo, valor);
    }

}
