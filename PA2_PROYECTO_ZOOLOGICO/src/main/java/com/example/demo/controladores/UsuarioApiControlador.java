package com.example.demo.controladores;

import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.UsuarioServicios;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ws/usuario")
public class UsuarioApiControlador {
   
    @Autowired
    private UsuarioServicios servicio;
    
    @GetMapping(value="/")
    public List<Usuario> getTodos(){
        return servicio.getTodos();
    }
    
    
    @GetMapping(value = "/{id}")
    public Optional<Usuario> getValor(@PathVariable Long id) {
        return servicio.getValor(id);
    }
    
    @PostMapping(value="/guardar")
    public Usuario guardar(@RequestBody Usuario entidad){
        
        return servicio.guardar(entidad);
    }
        
    @GetMapping(value="/eliminar/{id}")
    public Optional<Usuario> eliminar(@PathVariable Long id) {
        Optional<Usuario> temp = getValor(id);
        servicio.eliminar(id);
        return temp;
    }
    
}
