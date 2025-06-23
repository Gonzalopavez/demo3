package com.example.demo3.service;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Model.Soporte;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.AsignacionRepository;
import com.example.demo3.Repository.SoporteRepository;
import com.example.demo3.Repository.TicketRepository;
import com.example.demo3.dto.AsignacionesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsignacionServiceTest {

    @Mock
    private AsignacionRepository asignacionRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private AsignacionService asignacionService;

    private Asignacion asignacion;
    private Ticket ticket;
    private Soporte soporte;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setId(1L);

        soporte = new Soporte();
        soporte.setId(2L);

        asignacion = new Asignacion();
        asignacion.setId(10L);
        asignacion.setFechaAsignacion(LocalDate.now());
        asignacion.setTicket(ticket);
        asignacion.setSoporte(soporte);
    }

    @Test
    public void testGuardarAsignacionConDTO() {
        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setFechaAsignacion(LocalDate.now());
        dto.setTicketId(1L);
        dto.setSoporteId(2L);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(soporteRepository.findById(2L)).thenReturn(Optional.of(soporte));
        when(asignacionRepository.save(any(Asignacion.class))).thenReturn(asignacion);

        Asignacion resultado = asignacionService.guardarAsignacion(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getTicket().getId());
        assertEquals(2L, resultado.getSoporte().getId());
    }

    @Test
    public void testObtenerTodas() {
        when(asignacionRepository.findAll()).thenReturn(Arrays.asList(asignacion));
        List<Asignacion> lista = asignacionService.obtenerTodas();
        assertEquals(1, lista.size());
    }

    @Test
    public void testObtenerPorId() {
        when(asignacionRepository.findById(10L)).thenReturn(Optional.of(asignacion));
        Asignacion resultado = asignacionService.obtenerPorId(10L);
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
    }

    @Test
    public void testEliminarAsignacion() {
        asignacionService.eliminarAsignacion(10L);
        verify(asignacionRepository, times(1)).deleteById(10L);
    }

    @Test
    public void testActualizarAsignacionConDTO() {
        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setFechaAsignacion(LocalDate.now());
        dto.setTicketId(1L);
        dto.setSoporteId(2L);

        when(asignacionRepository.findById(10L)).thenReturn(Optional.of(asignacion));
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(soporteRepository.findById(2L)).thenReturn(Optional.of(soporte));
        when(asignacionRepository.save(any(Asignacion.class))).thenReturn(asignacion);

        Asignacion actualizado = asignacionService.actualizarAsignacion(10L, dto);

        assertNotNull(actualizado);
        assertEquals(1L, actualizado.getTicket().getId());
        assertEquals(2L, actualizado.getSoporte().getId());
    }

    @Test
    public void testReasignarSoporte() {
        Soporte nuevoSoporte = new Soporte();
        nuevoSoporte.setId(99L);

        when(asignacionRepository.findById(10L)).thenReturn(Optional.of(asignacion));
        when(soporteRepository.findById(99L)).thenReturn(Optional.of(nuevoSoporte));
        when(asignacionRepository.save(any(Asignacion.class))).thenReturn(asignacion);

        Asignacion resultado = asignacionService.reasignarSoporte(10L, 99L);

        assertEquals(99L, resultado.getSoporte().getId());
    }
}

