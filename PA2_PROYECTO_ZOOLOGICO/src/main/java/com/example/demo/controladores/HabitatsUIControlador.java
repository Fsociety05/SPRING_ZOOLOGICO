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
import com.example.demo.modelos.Itinerario;
import com.example.demo.modelos.Usuario;
import com.example.demo.modelos.UsuarioLogueado;
import com.example.demo.servicios.ClimaServicios;
import com.example.demo.servicios.EspecieHabitatServicios;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
import com.example.demo.servicios.ItinerarioServicios;
import com.example.demo.servicios.UsuarioLogueadoServicios;
import com.example.demo.servicios.UsuarioServicios;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Buddys
 */
@Controller
public class HabitatsUIControlador {

    private boolean editando = false;

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

    @Autowired
    private UsuarioServicios serviciosUsuario;

    @Autowired
    private ItinerarioServicios serviciosItinerario;

    @Autowired
    private UsuarioLogueadoServicios serviciosUsuarioLogueado;

    @RequestMapping("/mantenimiento_habitats")
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        registrarUsuarioLogueado(model);
        setParametro(model, "lista_Habitats", servicio.getTodos());
        return "paginas/mantenimiento_habitats";
    }

    @RequestMapping("/vista_habitats")
    public String vista(Model model) {
        registrarUsuarioLogueado(model);
        setParametro(model, "listaHabitats", servicio.getTodos());
        return "paginas/vista_habitats";
    }

    @GetMapping("/eliminarEspecieHabitats/{id}/{id_especie}")
    public String vistaEliminarEspecie(@PathVariable("id") Long id_habitat, @PathVariable("id_especie") Long id_especie, Model modelo) {

        List<Especies> tempEspecie = new ArrayList<>();

        for (EspecieHabitat especieHabitat : servicioEspecieHabitat.getPorHabitat(id_habitat)) {

            if (especieHabitat.getId_especie() == id_especie) {
                servicioEspecieHabitat.eliminar(especieHabitat.getId());
            } else {
                tempEspecie.add(servicioEspecie.getValor(especieHabitat.getId_especie()).get());
            }

        }
        registrarUsuarioLogueado(modelo);
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
        registrarUsuarioLogueado(modelo);
        setParametro(modelo, "listaEspecies", tempEspecie);
        setParametro(modelo, "listaVulnerabilidad", servicioVulneravilidad.getTodos());
        setParametro(modelo, "listaEspecieHabitat", servicioEspecieHabitat.getPorHabitat(id)); //se agregan los roles al combobox

        System.out.println(id);
        return "paginas/ver_especie_habitat";
    }

    @GetMapping("/crear_habitats")
    public String irCrear_usuario(Model model) {
        registrarUsuarioLogueado(model);
        setParametro(model, "habitats", new Habitats());
        setParametro(model, "lista_clima", servicioClima.getTodos()); //se agregan los roles al combobox
        setParametro(model, "listaVegetaciones", servicioVegetacion.getTodos()); //se agregan los roles al combobox
        return "paginas/formHabitats";
    }

    @GetMapping("/actualizarHabitats/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {

        editando = true;
        registrarUsuarioLogueado(modelo);
        setParametro(modelo, "habitats", servicio.getValor(id));
        setParametro(modelo, "lista_clima", servicioClima.getTodos());
        setParametro(modelo, "listaVegetaciones", servicioVegetacion.getTodos()); //se agregan los roles al combobox
        return "paginas/formHabitats";
    }

    @PostMapping("/guardarHabitats")
    public String guardar(Habitats habitats, Model model, RedirectAttributes attribute) {

        if (habitats.getId_vegetacion() == 0) {
            editando = false;
            attribute.addFlashAttribute("error", "No hay una vegetacion seleccionada");
            return "redirect:/crear_habitats";
        }
        
//        if (habitats.getId_clima()== 0) {
//            editando = false;
//            attribute.addFlashAttribute("error", "No hay un clima seleccionado");
//            return "redirect:/crear_habitats";
//        }

        for (Habitats todo : servicio.getTodos()) {
            if (editando) {
                if (todo.getNombre().equals(habitats.getNombre())) {
                    if (todo.getId() == habitats.getId()) {

                    } else {
                        editando = false;
                        attribute.addFlashAttribute("error", "El nombre del habitats ya existe");
                        return "redirect:/mantenimiento_habitats";
                    }

                }
            } else {
                if (todo.getNombre().equals(habitats.getNombre())) {
                    editando = false;
                    attribute.addFlashAttribute("error", "El nombre del habitats ya existe");
                    return "redirect:/crear_habitats";
                }
            }

        }
        editando = false;
        habitats.setNombreVegetacion(servicioVegetacion.getValor(habitats.getId_vegetacion()).get().getNombre());
        attribute.addFlashAttribute("success", "Guardado correctamente");

        servicio.guardar(habitats);
        return "redirect:/crear_habitats";
    }

    @GetMapping("eliminarHabitats/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        for (Itinerario todo : serviciosItinerario.getTodos()) {
            if (todo.getId_habitat() == id) {
                attribute.addFlashAttribute("error", "No se puede eliminar ya que se esta utilizando el registro en un itinerario");
                return "redirect:/mantenimiento_habitats";
            }
        }
 
        servicio.eliminar(id);
        attribute.addFlashAttribute("success", "Eliminado correctamente");
        return "redirect:/mantenimiento_habitats";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
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
