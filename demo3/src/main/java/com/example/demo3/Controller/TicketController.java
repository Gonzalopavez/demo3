package com.example.demo3.Controller;

import com.example.demo3.Model.EstadoTicketEnum;
import com.example.demo3.Model.Ticket;
import com.example.demo3.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;


    @PutMapping("/{id}/atender")
public Ticket atenderTicket(@PathVariable int id) {
    return ticketRepository.findById(id).map(ticket -> {
        ticket.setEstado(EstadoTicketEnum.EN_PROGRESO);
        return ticketRepository.save(ticket);
    }).orElseThrow(() -> new RuntimeException("Ticket no encontrado"));



}



@PutMapping("/{id}/abrir")
public Ticket abrirTicket(@PathVariable int id) {
    return ticketRepository.findById(id).map(ticket -> {
        ticket.setEstado(EstadoTicketEnum.ABIERTO);
        return ticketRepository.save(ticket);
    }).orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
}





@PutMapping("/{id}/cerrar")
public Ticket cerrarTicket(@PathVariable int id) {
    return ticketRepository.findById(id).map(ticket -> {
        ticket.setEstado(EstadoTicketEnum.CERRADO);
        ticket.setFechaResolucion(new Date());
        return ticketRepository.save(ticket);
    }).orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
}






    // AQUI SE PUEDEN VER TODOS LOS TICKETS



    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }





    // AQUI PODEMOS OBTNER UN TICKET POR SU ID

    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable int id) {
        return ticketRepository.findById(id);
    }




    // AQUI PODEMOS CREAR UN NUEVO TICKET


    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }






    
    // AQUI PODEMOS ACTUALIZAR UN TICKET



    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticket.setTitulo(ticketDetails.getTitulo());
        ticket.setDescripcion(ticketDetails.getDescripcion());
        ticket.setEstado(ticketDetails.getEstado());
        ticket.setFechaCreacion(ticketDetails.getFechaCreacion());
        ticket.setFechaResolucion(ticketDetails.getFechaResolucion());
        ticket.setPrioridad(ticketDetails.getPrioridad());
        ticket.setTipoSoporte(ticketDetails.getTipoSoporte());
        ticket.setIdSolicitante(ticketDetails.getIdSolicitante());
        
        return ticketRepository.save(ticket);
    }







    // AQUI PODEMOS ELIMINAR UN TICKET


    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable int id) {
        ticketRepository.deleteById(id);
        return "Ticket eliminado correctamente";
    }
}
