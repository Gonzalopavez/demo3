package com.example.demo3.Controller;

import com.example.demo3.Model.Soporte;
import com.example.demo3.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soportes")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @PostMapping
    public Soporte createSoporte(@RequestBody Soporte soporte) {
        return soporteService.guardarSoporte(soporte);
    }

    @GetMapping
    public List<Soporte> getAllSoportes() {
        return soporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Soporte getSoporteById(@PathVariable Long id) {
        return soporteService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Soporte updateSoporte(@PathVariable Long id, @RequestBody Soporte soporteActualizado) {
        Soporte soporte = soporteService.obtenerPorId(id);
        if (soporte == null) {
            throw new RuntimeException("Soporte no encontrado");
        }
        soporte.setNombre(soporteActualizado.getNombre());
        soporte.setEspecialidad(soporteActualizado.getEspecialidad());
        return soporteService.guardarSoporte(soporte);
    }

    @DeleteMapping("/{id}")
    public String deleteSoporte(@PathVariable Long id) {
        soporteService.eliminarSoporte(id);
        return "Soporte eliminado";
    }
}
