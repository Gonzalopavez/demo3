package com.example.demo3.service;

import com.example.demo3.Model.Soporte;
import com.example.demo3.Repository.SoporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    private Soporte soporte;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        soporte = new Soporte();
        soporte.setId(1L);
        soporte.setNombre("Carlos");
        soporte.setEspecialidad("Redes");
    }

    @Test
    public void testGuardarSoporte() {
        when(soporteRepository.save(soporte)).thenReturn(soporte);
        Soporte resultado = soporteService.guardarSoporte(soporte);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    public void testObtenerTodos() {
        when(soporteRepository.findAll()).thenReturn(Arrays.asList(soporte));
        List<Soporte> lista = soporteService.obtenerTodos();
        assertEquals(1, lista.size());
    }

    @Test
    public void testObtenerPorId() {
        when(soporteRepository.findById(1L)).thenReturn(Optional.of(soporte));
        Soporte resultado = soporteService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Redes", resultado.getEspecialidad());
    }

    @Test
    public void testEliminarSoporte() {
        soporteService.eliminarSoporte(1L);
        verify(soporteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarSoporte() {
        Soporte actualizado = new Soporte();
        actualizado.setNombre("Juan");
        actualizado.setEspecialidad("Bases de datos");

        when(soporteRepository.findById(1L)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any(Soporte.class))).thenReturn(actualizado);

        Soporte resultado = soporteService.actualizarSoporte(1L, actualizado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Bases de datos", resultado.getEspecialidad());
    }
}

