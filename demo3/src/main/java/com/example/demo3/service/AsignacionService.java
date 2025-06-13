package com.example.demo3.service;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Repository.AsignacionRepository;
import com.example.demo3.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    
    public Asignacion guardarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    
    public List<Asignacion> obtenerTodas() {
        return asignacionRepository.findAll();
    }

    
    public Asignacion obtenerPorId(Long id) {
        return asignacionRepository.findById(id).orElse(null);
    }

    
    public void eliminarAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }
}
