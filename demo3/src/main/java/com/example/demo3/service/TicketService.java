package com.example.demo3.service;

import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.TicketRepository;
import com.example.demo3.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    
    public Ticket guardarTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    
    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    
    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    
    public void eliminarTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    
}
