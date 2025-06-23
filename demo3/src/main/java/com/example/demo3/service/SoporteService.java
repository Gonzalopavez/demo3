package com.example.demo3.service;

import com.example.demo3.Model.Soporte;
import com.example.demo3.Repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    public Soporte guardarSoporte(Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    public List<Soporte> obtenerTodos() {
        return soporteRepository.findAll();
    }

    public Soporte obtenerPorId(Long id) {
        return soporteRepository.findById(id).orElse(null);
    }

    public void eliminarSoporte(Long id) {
        soporteRepository.deleteById(id);
    }

    public Soporte actualizarSoporte(Long id, Soporte soporteActualizado) {
        Soporte soporte = obtenerPorId(id);
        if (soporte == null) {
            throw new RuntimeException("Soporte no encontrado");
        }

        soporte.setNombre(soporteActualizado.getNombre());
        soporte.setEspecialidad(soporteActualizado.getEspecialidad());

        return guardarSoporte(soporte);
    }
}
