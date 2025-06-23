package com.example.demo3.Controller;

//import com.example.demo3.Controller.TicketController;
import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.PrioridadEnum;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Model.TipoSoporteEnum;
import com.example.demo3.dto.TicketDTO;
import com.example.demo3.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    private Ticket ticket;
    private TicketDTO ticketDTO;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitulo("Prueba");
        ticket.setDescripcion("Desc");
        ticket.setEstado(EstadoTicketEnum.ABIERTO);
        ticket.setFechaCreacion(new Date());
        ticket.setPrioridad(PrioridadEnum.MEDIA);
        ticket.setTipoSoporte(TipoSoporteEnum.INTERNO);
        ticket.setIdSolicitante(100);

        ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setTitulo("Prueba");
        ticketDTO.setDescripcion("Desc");
        ticketDTO.setEstado(EstadoTicketEnum.ABIERTO);
        ticketDTO.setFechaCreacion(new Date());
        ticketDTO.setPrioridad(PrioridadEnum.MEDIA);
        ticketDTO.setTipoSoporte(TipoSoporteEnum.INTERNO);
        ticketDTO.setIdSolicitante(100);
    }

    @Test
    public void testCrearTicket() throws Exception {
        when(ticketService.guardarTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Prueba"));
    }

    @Test
    public void testObtenerTodos() throws Exception {
        when(ticketService.obtenerTodos()).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerPorId() throws Exception {
        when(ticketService.obtenerPorId(1L)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarTicket() throws Exception {
        doNothing().when(ticketService).eliminarTicket(1L);

        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket eliminado correctamente"));
    }

    @Test
    public void testAbrirTicket() throws Exception {
        ticket.setEstado(EstadoTicketEnum.ABIERTO);
        when(ticketService.abrirTicket(1L)).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1/abrir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ABIERTO"));
    }

    @Test
    public void testAtenderTicket() throws Exception {
        ticket.setEstado(EstadoTicketEnum.EN_PROGRESO);
        when(ticketService.atenderTicket(1L)).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1/atender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PROGRESO"));
    }

    @Test
    public void testCerrarTicket() throws Exception {
        ticket.setEstado(EstadoTicketEnum.CERRADO);
        ticket.setFechaResolucion(new Date());
        when(ticketService.cerrarTicket(1L)).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1/cerrar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CERRADO"));
    }

    @Test
    public void testActualizarTicket() throws Exception {
        when(ticketService.actualizarTicket(eq(1L), any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Prueba"));
    }
}
