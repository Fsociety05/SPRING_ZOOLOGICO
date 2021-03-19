/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.modelos.EspecieHabitat;
import com.example.demo.repositorios.EspecieHabitatRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class EspecieHabitatServicios {

    @Autowired
    private EspecieHabitatRepositorio repositorio;

    public EspecieHabitat guardar(EspecieHabitat entidad) {

        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public Optional<EspecieHabitat> getValor(Long id) {
        return repositorio.findById(id);
    }

    public List<EspecieHabitat> getTodos() {
        return (List<EspecieHabitat>) repositorio.findAll();
    }

    public List<EspecieHabitat> getPorHabitat(Long idHabitat) {

        List<EspecieHabitat> temp = new ArrayList<>();

        for (EspecieHabitat listaTodo : getTodos()) {

            if (listaTodo.getId_habitat() == idHabitat) {
                temp.add(listaTodo);
            }
        }

        return temp;
    }

}
