package com.example.demo3.dto;

import jakarta.validation.constraints.NotBlank;

public class SoporteDTO {

    private Long id;

    @NotBlank(message = "El nombre del soporte es obligatorio")
    private String nombre;

    @NotBlank(message = "La especialidad del soporte es obligatoria")
    private String especialidad;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
