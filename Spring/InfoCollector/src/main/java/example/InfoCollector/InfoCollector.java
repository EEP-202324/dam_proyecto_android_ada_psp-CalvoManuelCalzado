package example.InfoCollector;

import org.springframework.data.annotation.Id;

record InfoCollector (@Id Long id, String name, String surname, String email, int number, String country) {
	
	
}
