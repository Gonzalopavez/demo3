package com.example.demo3.Controller;

import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.Ticket;
import com.example.demo3.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // AQUI PODEMOS CAMBIAR EL ESTADO DE UN TICKET A "EN PROGRESO"
    @PutMapping("/{id}/atender")
    public Ticket atenderTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerPorId(id);
        if (ticket == null) throw new RuntimeException("Ticket no encontrado");

        ticket.setEstado(EstadoTicketEnum.EN_PROGRESO);
        return ticketService.guardarTicket(ticket);
    }

    // AQUI PODEMOS CAMBIAR EL ESTADO DE UN TICKET A "ABIERTO"
    @PutMapping("/{id}/abrir")
    public Ticket abrirTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerPorId(id);
        if (ticket == null) throw new RuntimeException("Ticket no encontrado");

        ticket.setEstado(EstadoTicketEnum.ABIERTO);
        return ticketService.guardarTicket(ticket);
    }

    // AQUI PODEMOS CAMBIAR EL ESTADO DE UN TICKET A "CERRADO"
    @PutMapping("/{id}/cerrar")
    public Ticket cerrarTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenerPorId(id);
        if (ticket == null) throw new RuntimeException("Ticket no encontrado");

        ticket.setEstado(EstadoTicketEnum.CERRADO);
        ticket.setFechaResolucion(new Date());
        return ticketService.guardarTicket(ticket);
    }

    // AQUI SE PUEDEN VER TODOS LOS TICKETS
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.obtenerTodos();
    }

    // AQUI PODEMOS OBTENER UN TICKET POR SU ID
    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable Long id) {
        return Optional.ofNullable(ticketService.obtenerPorId(id));
    }

    // AQUI PODEMOS CREAR UN NUEVO TICKET
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.guardarTicket(ticket);
    }

    // AQUI PODEMOS ACTUALIZAR UN TICKET
    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        Ticket ticket = ticketService.obtenerPorId(id);
        if (ticket == null) throw new RuntimeException("Ticket no encontrado");

        ticket.setTitulo(ticketDetails.getTitulo());
        ticket.setDescripcion(ticketDetails.getDescripcion());
        ticket.setEstado(ticketDetails.getEstado());
        ticket.setFechaCreacion(ticketDetails.getFechaCreacion());
        ticket.setFechaResolucion(ticketDetails.getFechaResolucion());
        ticket.setPrioridad(ticketDetails.getPrioridad());
        ticket.setTipoSoporte(ticketDetails.getTipoSoporte());
        ticket.setIdSolicitante(ticketDetails.getIdSolicitante());

        return ticketService.guardarTicket(ticket);
    }

    // AQUI PODEMOS ELIMINAR UN TICKET
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return "Ticket eliminado correctamente";
    }
}
