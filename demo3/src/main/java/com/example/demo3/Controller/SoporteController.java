package com.example.demo3.Controller;

    import com.example.demo3.Model.Soporte;
    import com.example.demo3.Repository.SoporteRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/soportes")
    public class SoporteController {
        

        @Autowired
        private SoporteRepository soporteRepository;

        @PostMapping
        public Soporte createSoporte(@RequestBody Soporte soporte) {
            return soporteRepository.save(soporte);
        }

        @GetMapping
        public List<Soporte> getAllSoportes() {
            return soporteRepository.findAll();
        }

        @GetMapping("/{id}")
        public Soporte getSoporteById(@PathVariable Long id) {
            return soporteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
        }

        @PutMapping("/{id}")
        public Soporte updateSoporte(@PathVariable Long id, @RequestBody Soporte soporteActualizado) {
            return soporteRepository.findById(id).map(soporte -> {
                soporte.setNombre(soporteActualizado.getNombre());
                soporte.setEspecialidad(soporteActualizado.getEspecialidad());
                return soporteRepository.save(soporte);
            }).orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
        }

        @DeleteMapping("/{id}")
        public String deleteSoporte(@PathVariable Long id) {
            soporteRepository.deleteById(id);
            return "Soporte eliminado";
        }
    }
