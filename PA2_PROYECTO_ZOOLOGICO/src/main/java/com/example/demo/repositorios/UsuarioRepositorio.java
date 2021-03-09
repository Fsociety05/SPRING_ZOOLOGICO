package com.example.demo.repositorios;

import com.example.demo.modelos.Usuarios;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepositorio extends CrudRepository<Usuarios, Long> {
    
}
