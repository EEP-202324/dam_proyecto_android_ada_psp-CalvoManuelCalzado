package example.InfoCollector;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/infocollectors") // Remove trailing slash here
public class InfoCollectorController {
    private final InfoCollectorRepository infoCollectorRepository;

    public InfoCollectorController(InfoCollectorRepository infoCollectorRepository) {
        this.infoCollectorRepository = infoCollectorRepository;
    }
    
    @GetMapping("/{requestedId}")
    private ResponseEntity<InfoCollector> findById(@PathVariable Long requestedId, Principal principal) {
        Optional<InfoCollector> infoCollectorOptional = Optional.ofNullable(infoCollectorRepository.findByIdAndOwner(requestedId, principal.getName()));
        if (infoCollectorOptional.isPresent()) {
            return ResponseEntity.ok(infoCollectorOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        
    @GetMapping
    private ResponseEntity<List<InfoCollector>> findAll(Pageable pageable, Principal principal) {
        Page<InfoCollector> page = infoCollectorRepository.findByOwner(principal.getName(),
                PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
            ));
        return ResponseEntity.ok(page.getContent());
    }

    
    @PostMapping
	private ResponseEntity<Void> createInfoCollector(@RequestBody InfoCollector newInfoCollectorRequest, UriComponentsBuilder ucb) {
    	InfoCollector savedInfoCollector = infoCollectorRepository.save(newInfoCollectorRequest);
	   URI locationOfNewInfoCollector = ucb
	            .path("infocollectors/{id}")
	            .buildAndExpand(savedInfoCollector.id())
	            .toUri();
	   return ResponseEntity.created(locationOfNewInfoCollector).build();
	}
}
	
