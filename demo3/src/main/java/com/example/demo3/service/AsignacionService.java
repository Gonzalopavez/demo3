package com.example.demo3.service;

import com.example.demo3.Model.Asignacion;
import java.util.List;

public interface AsignacionService {
    Asignacion guardarAsignacion(Asignacion asignacion);
    List<Asignacion> obtenerTodas();
    Asignacion obtenerPorId(Long id);
    void eliminarAsignacion(Long id);
}
