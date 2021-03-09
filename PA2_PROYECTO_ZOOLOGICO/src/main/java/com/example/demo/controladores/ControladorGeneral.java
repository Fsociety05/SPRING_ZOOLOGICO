/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Especies;
import com.example.demo.modelos.Rol;
import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.RolServicios;
import com.example.demo.servicios.UsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */
@Controller
public class ControladorGeneral {

    private boolean primerInicio = true;

    @Autowired
    private RolServicios servicioRol;

    @Autowired
    private EspecieServicios servicioEspecie;

    @Autowired
    private UsuarioServicios servicioUsuario;

    @RequestMapping("/")
    public String index(Model model) {
        //setParametro(model, "lista", servicio.getTodos());
        if (primerInicio) {
            cargarTablas();
            primerInicio = false;
        }

        return "index";
    }

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
        
        servicioUsuario.guardar(tempUser);
    }
}
