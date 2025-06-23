package com.example.demo3.assembler;

import com.example.demo3.Controller.TicketController;
import com.example.demo3.Model.Ticket;
import com.example.demo3.dto.TicketDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TicketModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<TicketDTO>> {

    @Override
    public EntityModel<TicketDTO> toModel(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitulo(ticket.getTitulo());
        dto.setDescripcion(ticket.getDescripcion());
        dto.setEstado(ticket.getEstado());
        dto.setFechaCreacion(ticket.getFechaCreacion());
        dto.setFechaResolucion(ticket.getFechaResolucion());
        dto.setPrioridad(ticket.getPrioridad());
        dto.setTipoSoporte(ticket.getTipoSoporte());
        dto.setIdSolicitante(ticket.getIdSolicitante());

        return EntityModel.of(dto,
                linkTo(methodOn(TicketController.class).getTicketById(ticket.getId())).withSelfRel(),
                linkTo(methodOn(TicketController.class).getAllTickets()).withRel("todos"),
                linkTo(methodOn(TicketController.class).deleteTicket(ticket.getId())).withRel("eliminar"),
                linkTo(methodOn(TicketController.class).abrirTicket(ticket.getId())).withRel("abrir"),
                linkTo(methodOn(TicketController.class).atenderTicket(ticket.getId())).withRel("en-progreso"),
                linkTo(methodOn(TicketController.class).cerrarTicket(ticket.getId())).withRel("cerrar")
        );
    }
}
