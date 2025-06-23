package com.example.demo3.Controller;

import com.example.demo3.Model.Soporte;
import com.example.demo3.assembler.SoporteModelAssembler;
import com.example.demo3.dto.SoporteDTO;
import com.example.demo3.service.SoporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/soportes")
public class SoporteController {

    @Autowired
    private SoporteModelAssembler soporteModelAssembler;


    @Autowired
    private SoporteService soporteService;

    // 1. Crear soporte
    @PostMapping
    public SoporteDTO createSoporte(@Valid @RequestBody SoporteDTO dto) {
        Soporte soporte = convertToEntity(dto);
        return convertToDTO(soporteService.guardarSoporte(soporte));
    }

    // 2. Obtener todos
    @GetMapping
public CollectionModel<EntityModel<SoporteDTO>> getAllSoportes() {
    List<EntityModel<SoporteDTO>> soportes = soporteService.obtenerTodos().stream()
            .map(soporteModelAssembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(soportes,
            linkTo(methodOn(SoporteController.class).getAllSoportes()).withSelfRel());
}


    // 3. Obtener por ID
    @GetMapping("/{id}")
public EntityModel<SoporteDTO> getSoporteById(@PathVariable Long id) {
    Soporte soporte = soporteService.obtenerPorId(id);
    return soporteModelAssembler.toModel(soporte);
}


    // 4. Eliminar soporte
    @DeleteMapping("/{id}")
    public String deleteSoporte(@PathVariable Long id) {
        soporteService.eliminarSoporte(id);
        return "Soporte eliminado";
    }

    // 5. Actualizar soporte
    @PutMapping("/{id}")
    public SoporteDTO updateSoporte(@PathVariable Long id, @Valid @RequestBody SoporteDTO dto) {
        Soporte actualizado = soporteService.actualizarSoporte(id, convertToEntity(dto));
        return convertToDTO(actualizado);
    }

    // Conversión a mano

    private SoporteDTO convertToDTO(Soporte soporte) {
        SoporteDTO dto = new SoporteDTO();
        dto.setId(soporte.getId());
        dto.setNombre(soporte.getNombre());
        dto.setEspecialidad(soporte.getEspecialidad());
        return dto;
    }

    private Soporte convertToEntity(SoporteDTO dto) {
        Soporte soporte = new Soporte();
        soporte.setId(dto.getId());
        soporte.setNombre(dto.getNombre());
        soporte.setEspecialidad(dto.getEspecialidad());
        return soporte;
    }
}

