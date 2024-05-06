package example.InfoCollector;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
