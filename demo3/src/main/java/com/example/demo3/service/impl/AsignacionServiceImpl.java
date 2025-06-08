package com.example.demo3.service.impl;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Repository.AsignacionRepository;
import com.example.demo3.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    public Asignacion guardarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    @Override
    public List<Asignacion> obtenerTodas() {
        return asignacionRepository.findAll();
    }

    @Override
    public Asignacion obtenerPorId(Long id) {
        return asignacionRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }
}
