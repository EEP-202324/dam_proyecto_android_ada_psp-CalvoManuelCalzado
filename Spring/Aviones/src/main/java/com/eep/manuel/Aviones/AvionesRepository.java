package com.eep.manuel.Aviones;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface AvionesRepository extends CrudRepository<Aviones, Integer>, PagingAndSortingRepository<Aviones, Integer> {

}

	

