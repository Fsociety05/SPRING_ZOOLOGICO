package com.example.demo.controladores;

import com.example.demo.modelos.Usuarios;
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

@Controller
public class UsuariosUIControlador {
    
    @Autowired
    private UsuarioServicios servicio;  
    
    @RequestMapping("/")
    public String index(Model model) {
        setParametro(model, "lista", servicio.getTodos());
        return "usuarios";
    }
    
    @RequestMapping("/usuarios")
    public String vista(Model model) {
        setParametro(model, "lista", servicio.getTodos());
        return "vista_usuario";
    }
    
    @GetMapping("/crear")
    public String irCrear(Model model) {
        setParametro(model, "usuarios", new Usuarios());
        return "usuarios";
    }
    
    @GetMapping("/actualizar/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo){
        setParametro(modelo, "usuarios", servicio.getValor(id));
        return "usuarios";
    }
    
    @GetMapping("/Generar")
    public String irGenerar() {
       Usuarios tem =new Usuarios();   
        servicio.guardar(tem);
        return "redirect:/vista_usuario";
    }
    
     @GetMapping("/ver")
    public String irVer() {
    return "redirect:/vista_usuario";
    }
    
    @PostMapping("/guardar")
    public void setParametro(Model model, String atributo, Object valor){
        model.addAttribute(atributo, valor);
    }
    
     @GetMapping("eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo) {
    Usuarios temp = servicio.getValor(id).get();
        servicio.eliminar(id);
        return "redirect:/";
    }

    
}
