package com.example.demo3.service;

import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Método para guardar un ticket

    public Ticket guardarTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    
    // Métodos para obtener todos los tickets
    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    // Métodos para obtener un ticket por ID

    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    // Métodos para eliminar un ticket por ID

    public void eliminarTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    //metodo para abrir, en progreso y cerrar tickets

    public Ticket abrirTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (ticket != null) {
            ticket.setEstado(EstadoTicketEnum.ABIERTO);
            return guardarTicket(ticket);
        } else {
            throw new RuntimeException("Ticket no encontrado");
        }
    }

    public Ticket atenderTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (ticket != null) {
            ticket.setEstado(EstadoTicketEnum.EN_PROGRESO);
            return guardarTicket(ticket);
        } else {
            throw new RuntimeException("Ticket no encontrado");
        }
    }

    public Ticket cerrarTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (ticket != null) {
            ticket.setEstado(EstadoTicketEnum.CERRADO);
            ticket.setFechaResolucion(new Date());
            return guardarTicket(ticket);
        } else {
            throw new RuntimeException("Ticket no encontrado");
        }
    }


    //metodo para actualizar un ticket

    public Ticket actualizarTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = obtenerPorId(id);
        if (ticket == null) {
            throw new RuntimeException("Ticket no encontrado");
        }

        ticket.setTitulo(ticketDetails.getTitulo());
        ticket.setDescripcion(ticketDetails.getDescripcion());
        ticket.setEstado(ticketDetails.getEstado());
        ticket.setFechaCreacion(ticketDetails.getFechaCreacion());
        ticket.setFechaResolucion(ticketDetails.getFechaResolucion());
        ticket.setPrioridad(ticketDetails.getPrioridad());
        ticket.setTipoSoporte(ticketDetails.getTipoSoporte());
        ticket.setIdSolicitante(ticketDetails.getIdSolicitante());

        return guardarTicket(ticket);
    }
}
