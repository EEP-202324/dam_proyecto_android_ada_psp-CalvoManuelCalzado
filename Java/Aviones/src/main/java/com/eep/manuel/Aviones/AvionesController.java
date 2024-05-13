package com.eep.manuel.Aviones;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/Aviones")
class AvionesController {

	private final AvionesRepository AvionesRepository;
	

    @Autowired
    public AvionesController(AvionesRepository avionesRepository) {
        this.AvionesRepository = avionesRepository;
    }

    @PostConstruct
    public void init() {
        Aviones avion1 = new Aviones(1, "Juan", "Perez", "Boeing 747");
        Aviones avion2 = new Aviones(2, "Pablo", "Posada", "Airbus A380");
        Aviones avion3 = new Aviones(3, "Roberto", "Lopez", "B-52");

        AvionesRepository.save(avion1);
        AvionesRepository.save(avion2);
        AvionesRepository.save(avion3);
    }

	@GetMapping("/{requestedId}")
	private ResponseEntity<Aviones> findById(@PathVariable Integer requestedId) {
		Optional<Aviones> AvionesOptional = AvionesRepository.findById(requestedId);
		if (AvionesOptional.isPresent()) {
			return ResponseEntity.ok(AvionesOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Object> createAviones(@RequestBody Aviones newAvionesRequest, UriComponentsBuilder ucb) {
	    try {
	        Aviones savedAviones = AvionesRepository.save(newAvionesRequest);
	        URI locationOfNewAviones = ucb.path("/Aviones/{id}").buildAndExpand(savedAviones.getId()).toUri();
	        return ResponseEntity.created(locationOfNewAviones).build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
	    }
	}

	@GetMapping
	private ResponseEntity<List<Aviones>> findAll(Pageable pageable) {
		Page<Aviones> page = AvionesRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
		return ResponseEntity.ok(page.getContent());
	}
	
	@PutMapping("/{requestedId}")
	private ResponseEntity<Void> putAviones(@PathVariable Integer requestedId, @RequestBody Aviones avionesUpdate, Principal principal) {
	    Optional<Aviones> avionesOptional = AvionesRepository.findById(requestedId);
	    if (avionesOptional.isPresent()) {
	        Aviones existingAviones = avionesOptional.get();
	        existingAviones.setName(avionesUpdate.getName());
	        existingAviones.setApellido(avionesUpdate.getApellido());
	        existingAviones.setAE(avionesUpdate.getAE());
	        AvionesRepository.save(existingAviones);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/{id}") public ResponseEntity<Void> deleteAviones(@PathVariable Integer id){
		AvionesRepository.deleteById(id);
		return ResponseEntity.noContent().build(); }
	
	
}

