package com.example.demo3.service.impl;

import com.example.demo3.Model.Soporte;
import com.example.demo3.Repository.SoporteRepository;
import com.example.demo3.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoporteServiceImpl implements SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    @Override
    public Soporte guardarSoporte(Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    @Override
    public List<Soporte> obtenerTodos() {
        return soporteRepository.findAll();
    }

    @Override
    public Soporte obtenerPorId(Long id) {
        return soporteRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarSoporte(Long id) {
        soporteRepository.deleteById(id);
    }
}
