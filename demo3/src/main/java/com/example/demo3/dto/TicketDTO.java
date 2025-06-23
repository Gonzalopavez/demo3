package com.example.demo3.dto;

import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.PrioridadEnum;
import com.example.demo3.Model.TipoSoporteEnum;
import jakarta.validation.constraints.*;
 
import java.util.Date;

public class TicketDTO {

    private Long id;

    //contiene las validaciones para los campos del ticket

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private EstadoTicketEnum estado;

    private Date fechaCreacion;

    private Date fechaResolucion;

    private PrioridadEnum prioridad;

    private TipoSoporteEnum tipoSoporte;

    @Min(value = 1, message = "El ID del solicitante debe ser mayor que 0")
    private int idSolicitante;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTicketEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicketEnum estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public PrioridadEnum getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadEnum prioridad) {
        this.prioridad = prioridad;
    }

    public TipoSoporteEnum getTipoSoporte() {
        return tipoSoporte;
    }

    public void setTipoSoporte(TipoSoporteEnum tipoSoporte) {
        this.tipoSoporte = tipoSoporte;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
}

