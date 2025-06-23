package com.example.demo3.assembler;

import com.example.demo3.Controller.SoporteController;
import com.example.demo3.Model.Soporte;
import com.example.demo3.dto.SoporteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<SoporteDTO>> {

    @Override
    public EntityModel<SoporteDTO> toModel(Soporte soporte) {
        SoporteDTO dto = new SoporteDTO();
        dto.setId(soporte.getId());
        dto.setNombre(soporte.getNombre());
        dto.setEspecialidad(soporte.getEspecialidad());

        return EntityModel.of(dto,
                linkTo(methodOn(SoporteController.class).getSoporteById(soporte.getId())).withSelfRel(),
                linkTo(methodOn(SoporteController.class).getAllSoportes()).withRel("todos"));
    }
}
