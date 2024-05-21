package com.eep.manuel.Aviones;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface AvionesRepository extends CrudRepository<Avion, Integer>, PagingAndSortingRepository<Avion, Integer> {
	
    // Consulta personalizada utilizando @Query
    @Query("SELECT a FROM Avion a WHERE a.ae = ?1")
    List<Avion> findByAe(String ae);
        
    @Query("SELECT a FROM Avion a WHERE a.name LIKE %?1%")
    List<Avion> findByNameContaining(String name);
}

	

