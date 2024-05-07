package example.InfoCollector;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/infocollectors/")
class InfoCollectorController {
	private final InfoCollectorRepository infoCollectorRepository;

	private InfoCollectorController(InfoCollectorRepository infoCollectorRepository) {
		this.infoCollectorRepository = infoCollectorRepository;
	}

	@GetMapping("/{requestedId}")
	private ResponseEntity<InfoCollector> findById(@PathVariable Long requestedId) {
		Optional<InfoCollector> cashCardOptional = infoCollectorRepository.findById(requestedId);
		if (cashCardOptional.isPresent()) {
			return ResponseEntity.ok(cashCardOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	private ResponseEntity<Void> createCashCard(@RequestBody InfoCollector newInfoCollectorRequest, UriComponentsBuilder ucb) {
		InfoCollector savedInfoCollector = infoCollectorRepository.save(newInfoCollectorRequest);
	   URI locationOfNewInfoCollector = ucb
	            .path("infocollectors/{id}")
	            .buildAndExpand(savedInfoCollector.id())
	            .toUri();
	   return ResponseEntity.created(locationOfNewInfoCollector).build();
	}
	
}
