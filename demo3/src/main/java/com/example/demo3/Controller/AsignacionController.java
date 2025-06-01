package com.example.demo3.Controller;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Model.Soporte;
import com.example.demo3.Repository.AsignacionRepository;
import com.example.demo3.Repository.TicketRepository;
import com.example.demo3.Repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SoporteRepository soporteRepository;

    // Obtener todas las asignaciones
    @GetMapping
    public List<Asignacion> getAllAsignaciones() {
        return asignacionRepository.findAll();
    }

    //  Crear una nueva asignación
    @PostMapping
    public Asignacion createAsignacion(@RequestBody Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    //  Actualizar una asignación existente
    @PutMapping("/{id}")
    public Asignacion updateAsignacion(@PathVariable Long id, @RequestBody Asignacion asignacionDetails) {
        Optional<Asignacion> optionalAsignacion = asignacionRepository.findById(id);

        if (optionalAsignacion.isPresent()) {
            Asignacion asignacion = optionalAsignacion.get();
            asignacion.setFechaAsignacion(asignacionDetails.getFechaAsignacion());

            // Relacionar Ticket y Soporte
            if (asignacionDetails.getTicket() != null) {
                Optional<Ticket> ticket = ticketRepository.findById(asignacionDetails.getTicket().getId());
                ticket.ifPresent(asignacion::setTicket);
            }

            if (asignacionDetails.getSoporte() != null) {
                Optional<Soporte> soporte = soporteRepository.findById(asignacionDetails.getSoporte().getId());
                soporte.ifPresent(asignacion::setSoporte);
            }

            return asignacionRepository.save(asignacion);
        } else {
            throw new RuntimeException("Asignación no encontrada");
        }
    }

    //  Eliminar una asignación
    @DeleteMapping("/{id}")
    public void deleteAsignacion(@PathVariable Long id) {
        asignacionRepository.deleteById(id);
    }

    //  Reasignar un soporte a una asignación
    @PutMapping("/{id}/reasignar")
    public ResponseEntity<Asignacion> reasignarSoporte(
            @PathVariable Long id,
            @RequestParam Long soporteId) {

        Optional<Asignacion> asignacionOptional = asignacionRepository.findById(id);
        Optional<Soporte> soporteOptional = soporteRepository.findById(soporteId);

        if (asignacionOptional.isPresent() && soporteOptional.isPresent()) {
            Asignacion asignacion = asignacionOptional.get();
            Soporte nuevoSoporte = soporteOptional.get();
            asignacion.setSoporte(nuevoSoporte); //  Actualiza el soporte
            asignacionRepository.save(asignacion); //  Guarda el cambio
            return ResponseEntity.ok(asignacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
