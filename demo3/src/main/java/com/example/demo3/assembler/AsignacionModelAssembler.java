package com.example.demo3.assembler;

import com.example.demo3.Controller.AsignacionController;
import com.example.demo3.Model.Asignacion;
import com.example.demo3.dto.AsignacionesDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AsignacionModelAssembler implements RepresentationModelAssembler<Asignacion, EntityModel<AsignacionesDTO>> {

    @Override
    public EntityModel<AsignacionesDTO> toModel(Asignacion asignacion) {
        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setId(asignacion.getId());
        dto.setFechaAsignacion(asignacion.getFechaAsignacion());
        dto.setTicketId(asignacion.getTicket().getId());
        dto.setSoporteId(asignacion.getSoporte().getId());

        return EntityModel.of(dto,
                linkTo(methodOn(AsignacionController.class).getAsignacionById(asignacion.getId())).withSelfRel(),
                linkTo(methodOn(AsignacionController.class).getAllAsignaciones()).withRel("todas")
        );
    }
}
