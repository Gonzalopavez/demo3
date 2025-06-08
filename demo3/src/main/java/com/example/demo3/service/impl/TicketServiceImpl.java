package com.example.demo3.service.impl;

import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.TicketRepository;
import com.example.demo3.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket guardarTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    
}
