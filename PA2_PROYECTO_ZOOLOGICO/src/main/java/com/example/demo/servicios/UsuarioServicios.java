/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.Usuario;
import com.example.demo.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class UsuarioServicios {
    
    @Autowired
    private UsuarioRepositorio repositorio;
    
    public Usuario guardar(Usuario entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Usuario> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Usuario> getTodos() {
        return (List<Usuario>) repositorio.findAll();
    }
    
}
