package com.example.demo3.service;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Model.Soporte;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.AsignacionRepository;
import com.example.demo3.Repository.SoporteRepository;
import com.example.demo3.Repository.TicketRepository;
import com.example.demo3.dto.AsignacionesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SoporteRepository soporteRepository;

    // Método para guardar una nueva asignación

    public Asignacion guardarAsignacion(AsignacionesDTO dto) {
    Asignacion asignacion = new Asignacion();
    asignacion.setFechaAsignacion(dto.getFechaAsignacion());

    // Cargar Ticket desde repositorio
    Ticket ticket = ticketRepository.findById(dto.getTicketId())
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    asignacion.setTicket(ticket);

    // Cargar Soporte desde repositorio
    Soporte soporte = soporteRepository.findById(dto.getSoporteId())
            .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
    asignacion.setSoporte(soporte);

    return asignacionRepository.save(asignacion);
}


    // Métodos para obtener asignaciones

    public List<Asignacion> obtenerTodas() {
        return asignacionRepository.findAll();
    }

    // Métodos para obtener asignaciones por ID

    public Asignacion obtenerPorId(Long id) {
        return asignacionRepository.findById(id).orElse(null);
    }


    
    // Método para reasignar un soporte a un ticket existente


    public Asignacion reasignarSoporte(Long asignacionId, Long nuevoSoporteId) {
        Asignacion asignacion = obtenerPorId(asignacionId);
        if (asignacion == null) throw new RuntimeException("Asignación no encontrada");

        Soporte soporte = soporteRepository.findById(nuevoSoporteId)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));

        asignacion.setSoporte(soporte);
        return asignacionRepository.save(asignacion);
    }

    //metodo para eliminar una asignación por ID

    public void eliminarAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }

    // Métodos para actualizar una asignación

    public Asignacion actualizarAsignacion(Long id, AsignacionesDTO dto) {
    Asignacion asignacion = obtenerPorId(id);
    if (asignacion == null) {
        throw new RuntimeException("Asignación no encontrada");
    }

    asignacion.setFechaAsignacion(dto.getFechaAsignacion());

    // Ticket
    Ticket ticket = ticketRepository.findById(dto.getTicketId())
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    asignacion.setTicket(ticket);

    // Soporte
    Soporte soporte = soporteRepository.findById(dto.getSoporteId())
            .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
    asignacion.setSoporte(soporte);

    return asignacionRepository.save(asignacion);
}


}
