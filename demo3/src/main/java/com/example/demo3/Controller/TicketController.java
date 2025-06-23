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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;





import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketModelAssembler ticketModelAssembler;


    @Autowired
    private TicketService ticketService;

    // 1. Crear Ticket
    @PostMapping
    public TicketDTO createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);
        Ticket creado = ticketService.guardarTicket(ticket);
        return convertToDTO(creado);
    }

    // 2. Obtener todos los tickets
    @GetMapping
public CollectionModel<EntityModel<TicketDTO>> getAllTickets() {
    List<EntityModel<TicketDTO>> tickets = ticketService.obtenerTodos().stream()
            .map(ticketModelAssembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(tickets,
            linkTo(methodOn(TicketController.class).getAllTickets()).withSelfRel());
}


    // 3. Obtener ticket por ID
   @GetMapping("/{id}")
    public EntityModel<TicketDTO> getTicketById(@PathVariable Long id) {
    Ticket ticket = ticketService.obtenerPorId(id);
    return ticketModelAssembler.toModel(ticket);
}


    // 4. Eliminar ticket
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return "Ticket eliminado correctamente";
    }

    // 5. Cambiar estado a ABIERTO
    @PutMapping("/{id}/abrir")
    public TicketDTO abrirTicket(@PathVariable Long id) {
        return convertToDTO(ticketService.abrirTicket(id));
    }

    // 6. Cambiar estado a EN_PROGRESO
    @PutMapping("/{id}/atender")
    public TicketDTO atenderTicket(@PathVariable Long id) {
        return convertToDTO(ticketService.atenderTicket(id));
    }

    // 7. Cambiar estado a CERRADO
    @PutMapping("/{id}/cerrar")
    public TicketDTO cerrarTicket(@PathVariable Long id) {
        return convertToDTO(ticketService.cerrarTicket(id));
    }

    // 8. Actualizar Ticket
    @PutMapping("/{id}")
    public TicketDTO updateTicket(@PathVariable Long id, @Valid @RequestBody TicketDTO ticketDTO) {
        Ticket ticketActualizado = ticketService.actualizarTicket(id, convertToEntity(ticketDTO));
        return convertToDTO(ticketActualizado);
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