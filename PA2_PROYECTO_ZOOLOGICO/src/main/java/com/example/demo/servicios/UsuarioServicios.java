package com.example.demo.servicios;

import com.example.demo.modelos.Usuarios;
import com.example.demo.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicios {
        
    @Autowired
    private UsuarioRepositorio repositorio;
        public Usuarios guardar(Usuarios entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Usuarios> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Usuarios> getTodos() {
        return (List<Usuarios>) repositorio.findAll();
    }
}
