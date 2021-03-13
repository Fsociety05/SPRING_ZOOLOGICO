/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Clima;
import com.example.demo.modelos.Especies;
import com.example.demo.modelos.Vegetacion;
import com.example.demo.modelos.IndiceVulnerabilidad;
import com.example.demo.modelos.Habitats;
import com.example.demo.modelos.Itinerario;
import com.example.demo.modelos.Rol;
import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.ClimaServicios;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.IndiceVulnerabilidadServicios;
import com.example.demo.servicios.HabitatsServicios;
import com.example.demo.servicios.ItinerarioServicios;
import com.example.demo.servicios.RolServicios;
import com.example.demo.servicios.UsuarioServicios;
import com.example.demo.servicios.VegetacionServicio;
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
 * @author Licona
 */
@Controller
public class ControladorGeneral {

    private boolean primerInicio = true;
    Usuario usuario_logueado;
//////////////////////////////////////////////
    @Autowired
    private RolServicios servicioRol;
    
    @Autowired
    private HabitatsServicios servicioHabitat;
    
    @Autowired
    private ClimaServicios servicioClima;

    @Autowired
    private EspecieServicios servicioEspecie;

    @Autowired
    private UsuarioServicios servicioUsuario;
    
    @Autowired
    private IndiceVulnerabilidadServicios servicioIndice;
    
    @Autowired
    private VegetacionServicio servicioVegetacion;
    
    @Autowired
    private ItinerarioServicios servicioItinerario;

    @RequestMapping("/")
    public String index(Model model) {
        //setParametro(model, "lista", servicio.getTodos());
        if (primerInicio) {
            cargarTablas();
            primerInicio = false;
        }
        
         setParametro(model, "registro", new Usuario());

        return "index";
    }
    
    @RequestMapping("/inicio")
    public String inicio(Model model) {
        setParametro(model, "registro", usuario_logueado);

        return "paginas/inicio";
    }
    
    
    @PostMapping("/login_usuario")
    public String guardar(Usuario usuario, Model model, RedirectAttributes attribute) {
        boolean usuarioEncontrado = false;
        List<Usuario> lista_usuarios = servicioUsuario.getTodos();
        
        for(Usuario temp:lista_usuarios){
            
            if(temp.getNom_usuario().equals(usuario.getNom_usuario())){
                if(temp.getContrasenia().equals(usuario.getContrasenia())){
                    usuario_logueado=temp;
                    return "redirect:/inicio";
                }
                usuarioEncontrado = true;
            }
        }
        
        if(usuarioEncontrado){
            attribute.addFlashAttribute("error", "Contraseña incorrecta");
        }else{
            attribute.addFlashAttribute("error", "Usuario no encontrado"); //este es el mensage que quiero mostrar
        }
        
        

        return "redirect:/";
    }
    
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
    //////////////////////////////////////////////////////Intinerario
    @GetMapping("/crear_itinerario")
    public String irCrear_itinerario(Model model) {
        setParametro(model, "itinerario", new Itinerario());
        setParametro(model, "lista_habitat_combo", servicioHabitat.getTodos());
        setParametro(model, "registro", usuario_logueado);
        return "paginas/formItinerario";
    }
    
    @RequestMapping("/mantenimiento_itinerario" )
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        //verificacion();
        setParametro(model, "lista_Itinerario", servicioItinerario.getTodos());
        setParametro(model, "registro", usuario_logueado);
        return "paginas/mantenimiento_itinerario";
    }

    @RequestMapping("/vista_itinerario")
    public String vista(Model model) {
        //verificacion();
        setParametro(model, "lista_Itinerario", servicioItinerario.getTodos());
        setParametro(model, "registro", usuario_logueado);
        return "paginas/vista_Itinerario";
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void cargarTablas() {
        Rol temp = new Rol();
        Rol temp2 = new Rol();

        temp.setNombre("ADMINISTRADOR");
        temp.setDescripcion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        servicioRol.guardar(temp);

        temp2.setNombre("EMPLEADO");
        temp2.setDescripcion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        servicioRol.guardar(temp2);

        ////////////////////////////////////////////////////////////////////////////
        Especies tempEsp = new Especies();
        Especies tempEsp2 = new Especies();

        tempEsp.setNombreComun("Generado 1");
        tempEsp.setNombreCientifico("Generado 1 cientifico xd");
        tempEsp.setDescripcion("Este es una especie generada automaticamenta");
        tempEsp.setFoto("/images/defecto.png");

        servicioEspecie.guardar(tempEsp);

        tempEsp2.setNombreComun("Generado 2");
        tempEsp2.setNombreCientifico("Generado 2 cientifico xd");
        tempEsp2.setDescripcion("Este es una especie generada automaticamenta");
        tempEsp2.setFoto("/images/defecto.png");

        servicioEspecie.guardar(tempEsp2);
        
        ////////////////////////////////////////////////////////////////////////////////
        Usuario tempUser = new Usuario();
        
        tempUser.setNom_usuario("admin");
        tempUser.setContrasenia("admin");
        tempUser.setId_rol(temp.getId());
        tempUser.setDni("00");
        
        servicioUsuario.guardar(tempUser);
        Clima temClima1 = new Clima();
        Clima tempClima2 = new Clima();
        Clima tempClima3 = new Clima();

        temClima1.setNombre("Clima Calido");
        temClima1.setDescripcion("Temperaturas elevadas más constantemente");
        servicioClima.guardar(temClima1);

        tempClima2.setNombre("Clima Templado");
        tempClima2.setDescripcion("Instancia intermedia entre los cálidos y los fríos");
        servicioClima.guardar(tempClima2);
        
        tempClima3.setNombre("Clima Frio");
        tempClima3.setDescripcion("Aquellos en los que predominan las temperaturas bajas a lo largo del anio");
        servicioClima.guardar(tempClima3);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        IndiceVulnerabilidad tempVulne = new IndiceVulnerabilidad();
        tempVulne.setNombre_indice("Bajo");
        tempVulne.setDescripcion("");
        servicioIndice.guardar(tempVulne);
        
        IndiceVulnerabilidad tempVulne2 = new IndiceVulnerabilidad();
        tempVulne2.setNombre_indice("Medio");
        tempVulne2.setDescripcion("");
        servicioIndice.guardar(tempVulne2);

        
        IndiceVulnerabilidad tempVulne3 = new IndiceVulnerabilidad();
        tempVulne3.setNombre_indice("Alto");
        tempVulne3.setDescripcion("");
        servicioIndice.guardar(tempVulne3);


///////////////////////////////////////////////////////////////////////////////
        Vegetacion tempVege3 = new Vegetacion();
        tempVege3.setNombre("Bosques de Sabana");
        tempVege3.setDescripcion("regiones entre el desierto y el bosque tropical.");
        servicioVegetacion.guardar(tempVege3);
    }
}
