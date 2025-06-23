package com.example.demo3.Controller;

import com.example.demo3.Model.Ticket;
import com.example.demo3.assembler.TicketModelAssembler;
import com.example.demo3.dto.TicketDTO;
import com.example.demo3.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketModelAssembler ticketModelAssembler;

    // 1. Crear Ticket
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);
        Ticket creado = ticketService.guardarTicket(ticket);
        return ResponseEntity.ok(convertToDTO(creado));
    }

    // 2. Obtener todos los tickets
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TicketDTO>>> getAllTickets() {
        List<EntityModel<TicketDTO>> tickets = ticketService.obtenerTodos().stream()
                .map(ticketModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(tickets,
                        linkTo(methodOn(TicketController.class).getAllTickets()).withSelfRel()));
    }

    // 3. Obtener ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketDTO>> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerPorId(id);
        return ResponseEntity.ok(ticketModelAssembler.toModel(ticket));
    }

    // 4. Eliminar ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // 5. Cambiar estado a ABIERTO
    @PutMapping("/{id}/abrir")
    public ResponseEntity<TicketDTO> abrirTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.abrirTicket(id);
        return ResponseEntity.ok(convertToDTO(ticket));
    }

    // 6. Cambiar estado a EN_PROGRESO
    @PutMapping("/{id}/atender")
    public ResponseEntity<TicketDTO> atenderTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.atenderTicket(id);
        return ResponseEntity.ok(convertToDTO(ticket));
    }

    // 7. Cambiar estado a CERRADO
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<TicketDTO> cerrarTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.cerrarTicket(id);
        return ResponseEntity.ok(convertToDTO(ticket));
    }

    // 8. Actualizar Ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id,
     @Valid @RequestBody TicketDTO ticketDTO) {
        Ticket actualizado = ticketService.actualizarTicket(id, convertToEntity(ticketDTO));
        return ResponseEntity.ok(convertToDTO(actualizado));
    }

    // Métodos auxiliares
    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitulo(ticket.getTitulo());
        dto.setDescripcion(ticket.getDescripcion());
        dto.setEstado(ticket.getEstado());
        dto.setFechaCreacion(ticket.getFechaCreacion());
        dto.setFechaResolucion(ticket.getFechaResolucion());
        dto.setPrioridad(ticket.getPrioridad());
        dto.setTipoSoporte(ticket.getTipoSoporte());
        dto.setIdSolicitante(ticket.getIdSolicitante());
        return dto;
    }

    private Ticket convertToEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setTitulo(dto.getTitulo());
        ticket.setDescripcion(dto.getDescripcion());
        ticket.setEstado(dto.getEstado());
        ticket.setFechaCreacion(dto.getFechaCreacion());
        ticket.setFechaResolucion(dto.getFechaResolucion());
        ticket.setPrioridad(dto.getPrioridad());
        ticket.setTipoSoporte(dto.getTipoSoporte());
        ticket.setIdSolicitante(dto.getIdSolicitante());
        return ticket;
    }
}

