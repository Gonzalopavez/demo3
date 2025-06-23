package com.example.demo3.Controller;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Model.Soporte;
import com.example.demo3.dto.AsignacionesDTO;
import com.example.demo3.service.AsignacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AsignacionController.class)
public class AsignacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignacionService asignacionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Asignacion asignacion;

    @BeforeEach
    public void setUp() {
        asignacion = new Asignacion();
        asignacion.setId(1L);
        asignacion.setFechaAsignacion(LocalDate.now());

        Ticket ticket = new Ticket();
        ticket.setId(10L);
        asignacion.setTicket(ticket);

        Soporte soporte = new Soporte();
        soporte.setId(20L);
        asignacion.setSoporte(soporte);
    }

    // 1. Crear asignación
    @Test
    public void testCrearAsignacion() throws Exception {
        when(asignacionService.guardarAsignacion(any())).thenReturn(asignacion);

        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setId(1L);
        dto.setFechaAsignacion(LocalDate.now());
        dto.setTicketId(10L);
        dto.setSoporteId(20L);

        mockMvc.perform(post("/api/asignaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(10))
                .andExpect(jsonPath("$.soporteId").value(20));
    }

    // 2. Obtener todas las asignaciones
    @Test
    public void testObtenerTodasAsignaciones() throws Exception {
        when(asignacionService.obtenerTodas()).thenReturn(List.of(asignacion));

        mockMvc.perform(get("/api/asignaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketId").value(10))
                .andExpect(jsonPath("$[0].soporteId").value(20));
    }

    // 3. Obtener asignación por ID
    @Test
    public void testObtenerAsignacionPorId() throws Exception {
        when(asignacionService.obtenerPorId(1L)).thenReturn(asignacion);

        mockMvc.perform(get("/api/asignaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(10))
                .andExpect(jsonPath("$.soporteId").value(20));
    }

    // 4. Actualizar asignación
    @Test
    public void testActualizarAsignacion() throws Exception {
        when(asignacionService.actualizarAsignacion(eq(1L), any())).thenReturn(asignacion);

        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setId(1L);
        dto.setFechaAsignacion(LocalDate.now());
        dto.setTicketId(10L);
        dto.setSoporteId(20L);

        mockMvc.perform(put("/api/asignaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(10))
                .andExpect(jsonPath("$.soporteId").value(20));
    }

    // 5. Reasignar soporte
    @Test
    public void testReasignarSoporte() throws Exception {
        when(asignacionService.reasignarSoporte(1L, 20L)).thenReturn(asignacion);

        mockMvc.perform(put("/api/asignaciones/1/reasignar?soporteId=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soporteId").value(20));
    }
}
