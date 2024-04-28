package example.pakamaka;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pakamakas")
class PakaMakaController {

    @GetMapping("/{requestedId}")
    private ResponseEntity<PakaMaka> findById() {
       PakaMaka pakaMaka = new PakaMaka(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España");
       return ResponseEntity.ok(pakaMaka);
    }
}
