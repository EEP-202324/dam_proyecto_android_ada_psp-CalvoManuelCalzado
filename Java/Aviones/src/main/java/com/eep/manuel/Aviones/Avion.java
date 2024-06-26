package com.eep.manuel.Aviones;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "surname")
    private String apellido;
    
    @Column(name = "ae")
    private String ae;

    public Avion() {
    }
        
    public Avion(Integer id, String name, String apellido, String ae) {
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
		this.ae = aE;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Avion other = (Avion) obj;
	    return Objects.equals(id, other.id) &&
	           Objects.equals(name, other.name) &&
	           Objects.equals(apellido, other.apellido) &&
	           Objects.equals(ae, other.ae);
	}
}
