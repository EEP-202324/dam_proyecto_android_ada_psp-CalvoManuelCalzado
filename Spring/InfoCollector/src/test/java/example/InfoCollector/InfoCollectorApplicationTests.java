package example.InfoCollector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfoCollectorApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAInfoCollectorWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.getForEntity("/infocollectors/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("Jose");

		String surname = documentContext.read("$.surname");
		assertThat(surname).isEqualTo("Garcia");

		String email = documentContext.read("$.email");
		assertThat(email).isEqualTo("josegarcia@gmail.com");

		int number = documentContext.read("$.number");
		assertThat(number).isEqualTo(640882919);

		String country = documentContext.read("$.country");
		assertThat(country).isEqualTo("España");
	}

	@Test
	void shouldNotReturnAInfoCollectorWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/infocollectors/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
		
	}
	
	@Test
	void shouldCreateANewInfoCollector() {
		InfoCollector newInfoCollector = new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España");
	   ResponseEntity<Void> createResponse = restTemplate.postForEntity("/infocollectors", newInfoCollector, Void.class);
	   assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	   URI locationOfNewInfoCollector = createResponse.getHeaders().getLocation();
	   ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewInfoCollector, String.class);
	   assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	   
	   DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("Jose");

		String surname = documentContext.read("$.surname");
		assertThat(surname).isEqualTo("Garcia");

		String email = documentContext.read("$.email");
		assertThat(email).isEqualTo("josegarcia@gmail.com");

		int number = documentContext.read("$.number");
		assertThat(number).isEqualTo(640882919);

		String country = documentContext.read("$.country");
		assertThat(country).isEqualTo("España");
	}

}