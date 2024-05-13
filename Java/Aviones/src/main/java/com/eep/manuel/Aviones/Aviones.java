package com.eep.manuel.Aviones;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aviones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String apellido;
    private String AE;

    public Aviones() {
    }
    
    public Aviones(Integer id, String name, String apellido, String AE) {
        this.id = id;
        this.name = name;
        this.apellido = apellido;
        this.AE = AE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAE() {
        return AE;
    }

    public void setAE(String AE) {
        this.AE = AE;
    }
}
