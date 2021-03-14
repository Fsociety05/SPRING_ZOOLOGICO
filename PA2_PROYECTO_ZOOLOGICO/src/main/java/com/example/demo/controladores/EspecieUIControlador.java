/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Especies;
import com.example.demo.modelos.Usuario;
import com.example.demo.modelos.UsuarioLogueado;
import com.example.demo.servicios.EspecieServicios;
import com.example.demo.servicios.UsuarioLogueadoServicios;
import com.example.demo.servicios.UsuarioServicios;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Buddys
 */
@Controller
public class EspecieUIControlador {

    private String nomFoto = "null";
    private boolean editando = false;

    @Autowired
    private EspecieServicios servicio;
    
    @Autowired
    private UsuarioServicios serviciosUsuario;

    @Autowired
    private UsuarioLogueadoServicios serviciosUsuarioLogueado;

    @RequestMapping("/mantenimiento_especie")
    public String irMantenimiento(Model model, RedirectAttributes attribute) {
        
        registrarUsuarioLogueado(model);
        setParametro(model, "lista", servicio.getTodos());
        return "paginas/mantenimiento_especies";
    }

    @RequestMapping("/vista_especie")
    public String vista(Model model) {
        registrarUsuarioLogueado(model);
        setParametro(model, "lista", servicio.getTodos());
        return "paginas/vista_especie";
    }

    @GetMapping("/crear")
    public String irCrear(Model model) {
        registrarUsuarioLogueado(model);
        setParametro(model, "especie", new Especies());
        return "paginas/form_especies";
    }

    @GetMapping("/actualizar/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo) {
        nomFoto = servicio.getValor(id).get().getFoto();
        setParametro(modelo, "especie", servicio.getValor(id));
        registrarUsuarioLogueado(modelo);
        editando = true;
        return "paginas/form_especies";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("foto1") MultipartFile file, Especies especie, Model model, RedirectAttributes attribute) {

        for (Especies todo : servicio.getTodos()) {
            if (editando) {
                if (todo.getNombreComun().equals(especie.getNombreComun())) {
                    if (todo.getId() == especie.getId()) {

                    } else {
                        editando = false;
                        attribute.addFlashAttribute("error", "Nombre comun ya registrado");
                        return "redirect:/mantenimiento_especie";
                    }

                }

                if (todo.getNombreCientifico().equals(especie.getNombreCientifico())) {

                    if (todo.getId() == especie.getId()) {

                    } else {
                        editando = false;
                        attribute.addFlashAttribute("error", "Nombre cientifico ya registrado");
                        return "redirect:/mantenimiento_especie";
                    }

                }
            } else {
                if (todo.getNombreComun().equals(especie.getNombreComun())) {
                    attribute.addFlashAttribute("error", "Nombre comun ya registrado");
                    return "redirect:/crear";
                }

                if (todo.getNombreCientifico().equals(especie.getNombreCientifico())) {
                    attribute.addFlashAttribute("error", "Nombre cientifico ya registrado");
                    return "redirect:/crear";
                }
            }
        }

        editando = false;

        Path direcctorioImagenes = Paths.get("src//main//resources//static/images");

        String rutaAbsoluta = direcctorioImagenes.toFile().getAbsolutePath();
        try {
            if (!file.isEmpty()) {

                byte[] byteImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, byteImg);

                especie.setFoto("/images/" + file.getOriginalFilename());

            } else {
                //if(editando){

                if (nomFoto.equals("null")) {
                    especie.setFoto("/images/defecto.png");
                } else {
                    especie.setFoto(nomFoto);
                }

                //}
            }

        } catch (Exception e) {
            System.out.println("error en guardado " + e.getMessage());
            especie.setFoto("/images/defecto.png");
        }

        servicio.guardar(especie);
        nomFoto = "null";

        attribute.addFlashAttribute("success", "Guargado Correctamente");

        return "redirect:/crear";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

    @GetMapping("eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        try {
            Especies temp = servicio.getValor(id).get();

            //System.out.println(retornaNombre(temp.getFoto()));
            if (!retornaNombre(temp.getFoto()).equals("defecto.png")) {
                File imagen = new File("src\\main\\resources\\static\\images\\" + retornaNombre(temp.getFoto()));
                FileInputStream readImage = new FileInputStream(imagen);

                readImage.close();
                imagen.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        attribute.addFlashAttribute("error", "Eliminado correctamente");
        servicio.eliminar(id);
        return "redirect:/mantenimiento_especie";
    }
//////////////////////////////////////////////////////////////////////////////////////////////

    private String retornaNombre(String url) {
        String nombre = "";
        int num_plecas = 2;

        for (int i = 0; i < url.length(); i++) {

            if (num_plecas == 0) {
                nombre += Character.toString(url.charAt(i));
            }

            if (url.charAt(i) == '/') {
                num_plecas--;
            }

        }

        return nombre;
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
