package com.example.demo3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

//lo mas comun y limpio es que este dto trabaje con los ids de ticket y soporte, no con los objetos completos

public class AsignacionesDTO {

    private Long id;

    @NotNull(message = "La fecha de asignación es obligatoria")
    private LocalDate fechaAsignacion;

    @Min(value = 1, message = "Debe indicar el ID del ticket")
    private Long ticketId;

    @Min(value = 1, message = "Debe indicar el ID del soporte")
    private Long soporteId;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getSoporteId() {
        return soporteId;
    }

    public void setSoporteId(Long soporteId) {
        this.soporteId = soporteId;
    }
}
