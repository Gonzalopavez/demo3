package com.example.demo3.Controller;

import com.example.demo3.Model.Asignacion;
import com.example.demo3.assembler.AsignacionModelAssembler;
import com.example.demo3.dto.AsignacionesDTO;
import com.example.demo3.service.AsignacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionModelAssembler asignacionModelAssembler;


    @Autowired
    private AsignacionService asignacionService;

    // 1. Crear asignación
    @PostMapping
    public AsignacionesDTO createAsignacion(@Valid @RequestBody AsignacionesDTO dto) {
        return convertToDTO(asignacionService.guardarAsignacion(dto));
    }

    // 2. Obtener todas
    @GetMapping
public CollectionModel<EntityModel<AsignacionesDTO>> getAllAsignaciones() {
    List<EntityModel<AsignacionesDTO>> asignaciones = asignacionService.obtenerTodas().stream()
            .map(asignacionModelAssembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(asignaciones,
            linkTo(methodOn(AsignacionController.class).getAllAsignaciones()).withSelfRel());
}


    // 3. Obtener por ID
    @GetMapping("/{id}")
public EntityModel<AsignacionesDTO> getAsignacionById(@PathVariable Long id) {
    Asignacion asignacion = asignacionService.obtenerPorId(id);
    return asignacionModelAssembler.toModel(asignacion);
}


    // 4. Eliminar asignación
    @DeleteMapping("/{id}")
    public void deleteAsignacion(@PathVariable Long id) {
        asignacionService.eliminarAsignacion(id);
    }

    // 5. Reasignar soporte
    @PutMapping("/{id}/reasignar")
    public ResponseEntity<AsignacionesDTO> reasignarSoporte(@PathVariable Long id, @RequestParam Long soporteId) {
        try {
            Asignacion reasignada = asignacionService.reasignarSoporte(id, soporteId);
            return ResponseEntity.ok(convertToDTO(reasignada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Actualizar asignación
    @PutMapping("/{id}")
    public AsignacionesDTO updateAsignacion(@PathVariable Long id, @Valid @RequestBody AsignacionesDTO dto) {
        return convertToDTO(asignacionService.actualizarAsignacion(id, dto));
    }

    // Conversión a mano

    private AsignacionesDTO convertToDTO(Asignacion asignacion) {
        AsignacionesDTO dto = new AsignacionesDTO();
        dto.setId(asignacion.getId());
        dto.setFechaAsignacion(asignacion.getFechaAsignacion());
        dto.setTicketId(asignacion.getTicket().getId());
        dto.setSoporteId(asignacion.getSoporte().getId());
        return dto;
    }
}