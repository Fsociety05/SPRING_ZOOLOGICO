/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.modelos.Especies;
import com.example.demo.servicios.EspecieServicios;
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

/**
 *
 * @author Buddys
 */
@Controller
public class EspecieUIControlador {
    private String nomFoto = "null";
    
    @Autowired
    private EspecieServicios servicio;  
    
    @RequestMapping("/mantenimiento_especie")
    public String irMantenimiento(Model model) {
        setParametro(model, "lista", servicio.getTodos());
        return "paginas/mantenimiento_especies";
    }
    
    @RequestMapping("/vista_especie")
    public String vista(Model model) {
        setParametro(model, "lista", servicio.getTodos());
        return "paginas/vista_especie";
    }
    
    @GetMapping("/crear")
    public String irCrear(Model model) {
        setParametro(model, "especie", new Especies());
        return "paginas/form_especies";
    }
    
    @GetMapping("/actualizar/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo){
        nomFoto = servicio.getValor(id).get().getFoto();
        setParametro(modelo, "especie", servicio.getValor(id));
        return "paginas/form_especies";
    }
    
//    @GetMapping("/Generar")
//    public String irGenerar() {
//       Especies tem =new Especies();
//        tem.setNombreComun("Especie generada");
//        tem.setDescripcion("esta es una descripcion por defecto");
//        tem.setFoto("/images/defecto.png");
//        
//        servicio.guardar(tem);
//        return "redirect:/mantenimiento_especie";
//    }
    
    
    @PostMapping("/guardar")
    public String guardar(@RequestParam("foto1") MultipartFile file, Especies especie, Model model) {

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
                   
                   if(nomFoto.equals("null")){
                       especie.setFoto("/images/defecto.png");
                   }else{
                       especie.setFoto(nomFoto);
                   }
                   
               //}
            }

        } catch (Exception e) {
            System.out.println("error en guardado "+e.getMessage());
            especie.setFoto("/images/defecto.png");
        }

        servicio.guardar(especie);
        nomFoto="null";
        return "redirect:/mantenimiento_especie"; 
    }
    
    public void setParametro(Model model, String atributo, Object valor){
        model.addAttribute(atributo, valor);
    }
    
     @GetMapping("eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {

        try {
            Especies temp = servicio.getValor(id).get();

            //System.out.println(retornaNombre(temp.getFoto()));
            if (!retornaNombre(temp.getFoto()).equals("defecto.png")) {
                File imagen = new File("src\\main\\resources\\static\\images\\"+retornaNombre(temp.getFoto()));
                FileInputStream readImage = new FileInputStream(imagen);

                readImage.close();
                imagen.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
    
    
    
}
