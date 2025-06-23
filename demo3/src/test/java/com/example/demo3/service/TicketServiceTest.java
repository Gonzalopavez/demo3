package com.example.demo3.service;

import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitulo("Prueba");
        ticket.setEstado(EstadoTicketEnum.ABIERTO);
        ticket.setFechaCreacion(new Date());
        ticket.setIdSolicitante(123);
    }

    @Test
    public void testGuardarTicket() {
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        Ticket resultado = ticketService.guardarTicket(ticket);
        assertNotNull(resultado);
        assertEquals("Prueba", resultado.getTitulo());
    }

    @Test
    public void testObtenerTodos() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket));
        List<Ticket> lista = ticketService.obtenerTodos();
        assertEquals(1, lista.size());
    }

    @Test
    public void testObtenerPorId() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        Ticket resultado = ticketService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(123, resultado.getIdSolicitante());
    }

    @Test
    public void testEliminarTicket() {
        ticketService.eliminarTicket(1L);
        verify(ticketRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAbrirTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket actualizado = ticketService.abrirTicket(1L);
        assertEquals(EstadoTicketEnum.ABIERTO, actualizado.getEstado());
    }
}
