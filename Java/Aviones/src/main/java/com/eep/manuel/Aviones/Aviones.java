package com.eep.manuel.Aviones;

import java.util.Objects;

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
    private String ae;

    public Aviones() {
    }
        
    public Aviones(Integer id, String name, String apellido, String ae) {
        this.id = id;
        this.name = name;
        this.apellido = apellido;
        this.ae = ae;
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
		return ae;
	}

	public void setAE(String aE) {
		ae = aE;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Aviones other = (Aviones) obj;
	    return Objects.equals(id, other.id) &&
	           Objects.equals(name, other.name) &&
	           Objects.equals(apellido, other.apellido) &&
	           Objects.equals(ae, other.ae);
	}

	
}
