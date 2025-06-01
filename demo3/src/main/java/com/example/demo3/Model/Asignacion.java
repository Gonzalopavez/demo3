package com.example.demo3.Model;
import jakarta.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name = "asignaciones") // nombra explicitamente la tabla, si no se especifica, se usará el nombre de la clase en minúsculas
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;// Genera un ID único automáticamente

    @ManyToOne(fetch = FetchType.EAGER)  // Carga los datos completos del Ticket
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)  //  Carga los datos completos del Soporte
    @JoinColumn(name = "soporte_id")
    private Soporte soporte;




    private LocalDate fechaAsignacion;// Fecha en la que se asignó el ticket al soporte


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Soporte getSoporte() {
        return soporte;
    }

    public void setSoporte(Soporte soporte) {
        this.soporte = soporte;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }


    public void reasignarSoporte(Soporte nuevoSoporte) {
    this.soporte = nuevoSoporte;
}

}
