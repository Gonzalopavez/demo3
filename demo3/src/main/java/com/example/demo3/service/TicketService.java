package com.example.demo3.service;

import com.example.demo3.Model.Ticket;
import java.util.List;

public interface TicketService {

    Ticket guardarTicket(Ticket ticket);
    List<Ticket> obtenerTodos();
    Ticket obtenerPorId(Long id);
    void eliminarTicket(Long id);
    
}
