package com.example.demo3.Model;
import jakarta.persistence.Entity; // mi clase dera una entidad de JPA
import jakarta.persistence.GeneratedValue; // valor del id generado automáticamente
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; //atributo marcado con @Id es la clave primaria de la entidad

@Entity
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID único automáticamente


    private Long id;
    private String nombre;
    private String especialidad;






    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
