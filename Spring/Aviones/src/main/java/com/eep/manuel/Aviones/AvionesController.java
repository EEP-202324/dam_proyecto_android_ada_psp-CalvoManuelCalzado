package com.eep.manuel.Aviones;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@RestController
@RequestMapping("/Aviones")
class AvionesController {

	private final AvionesRepository AvionesRepository;

	private AvionesController(AvionesRepository AvionesRepository) {
		this.AvionesRepository = AvionesRepository;
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
	public ResponseEntity<Void> createAviones(@RequestBody Aviones newAvionesRequest, UriComponentsBuilder ucb) {
		Aviones savedAviones = AvionesRepository.save(newAvionesRequest);
		URI locationOfNewAviones = ucb.path("Aviones/{id}").buildAndExpand(savedAviones.getId()).toUri();
		return ResponseEntity.created(locationOfNewAviones).build();
	}

	@GetMapping
	private ResponseEntity<List<Aviones>> findAll(Pageable pageable) {
		Page<Aviones> page = AvionesRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
		return ResponseEntity.ok(page.getContent());
	}
	@PutMapping("/{requestedId}")
	private ResponseEntity<Void> putAviones(@PathVariable Integer requestedId, @RequestBody Aviones avionesUpdate, Principal principal) {
		Aviones updatedAviones = new Aviones(requestedId, avionesUpdate.getName(), avionesUpdate.getApellido(), avionesUpdate.getDE());
		AvionesRepository.save(updatedAviones);
	    return ResponseEntity.noContent().build();
	}
	@DeleteMapping("/{id}") public ResponseEntity<Void> deleteAviones(@PathVariable Integer id){
		AvionesRepository.deleteById(id);
		return ResponseEntity.noContent().build(); }
	
	
}

