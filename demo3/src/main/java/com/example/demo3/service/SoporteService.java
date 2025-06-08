package com.example.demo3.service;

import com.example.demo3.Model.Soporte;
import java.util.List;


public interface SoporteService {

    Soporte guardarSoporte(Soporte soporte);
    List<Soporte> obtenerTodos();
    Soporte obtenerPorId(Long id);
    void eliminarSoporte(Long id);


    
}
