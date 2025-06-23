package com.example.demo3.Controller;

//import com.example.demo3.Controller.SoporteController;
import com.example.demo3.Model.Soporte;
import com.example.demo3.dto.SoporteDTO;
import com.example.demo3.service.SoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoporteService soporteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Soporte soporte;

    @BeforeEach
    public void setUp() {
        soporte = new Soporte();
        soporte.setId(1L);
        soporte.setNombre("Carlos");
        soporte.setEspecialidad("Redes");
    }

    @Test
    public void testCrearSoporte() throws Exception {
        when(soporteService.guardarSoporte(any())).thenReturn(soporte);

        mockMvc.perform(post("/api/soportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    public void testObtenerTodos() throws Exception {
        when(soporteService.obtenerTodos()).thenReturn(List.of(soporte));

        mockMvc.perform(get("/api/soportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        when(soporteService.obtenerPorId(1L)).thenReturn(soporte);

        mockMvc.perform(get("/api/soportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    public void testupdateSoporte() throws Exception {
        when(soporteService.obtenerPorId(1L)).thenReturn(soporte);
        when(soporteService.actualizarSoporte(eq(1L), any())).thenReturn(soporte);


        SoporteDTO actualizado = new SoporteDTO();
        actualizado.setNombre("Carlos Editado");
        actualizado.setEspecialidad("Soporte Técnico");

        mockMvc.perform(put("/api/soportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especialidad").value("Redes"));
    }

    @Test
    public void testEliminarSoporte() throws Exception {
        doNothing().when(soporteService).eliminarSoporte(1L);

        mockMvc.perform(delete("/api/soportes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Soporte eliminado"));
    }
}

