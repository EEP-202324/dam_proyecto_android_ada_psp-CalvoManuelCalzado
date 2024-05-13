package example.InfoCollector;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class InfoCollectorApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAInfoCollectorWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("sarah1", "abc123")
				.getForEntity("/infocollectors/99", String.class);

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
	void shouldNotAllowAccessToInfoCollectorTheyDoNotOwn() {
	    ResponseEntity<String> response = restTemplate
	      .withBasicAuth("sarah1", "abc123")
	      .getForEntity("/infocollectors/102", String.class); // kumar2's data
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
		
	@Test
	void shouldRejectUsersWhoAreNotInfoOwners() {
	    ResponseEntity<String> response = restTemplate
	            .withBasicAuth("hank-owns-no-infos", "qrs456")
	            .getForEntity("/infocollectors/99", String.class);
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void shouldNotReturnAInfoCollectorWhenUsingBadCredentials() {
	    ResponseEntity<String> response = restTemplate
	      .withBasicAuth("BAD-USER", "abc123")
	      .getForEntity("/infocollectors/99", String.class);
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

	    response = restTemplate
	      .withBasicAuth("sarah1", "BAD-PASSWORD")
	      .getForEntity("/cashcards/99", String.class);
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	void shouldReturnAPageOfInfoCollector() {
	    ResponseEntity<String> response = restTemplate
	    		.withBasicAuth("sarah1", "abc123")
	    		.getForEntity("/infocollectors?page=0&size=1", String.class);
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	    DocumentContext documentContext = JsonPath.parse(response.getBody());
	    JSONArray page = documentContext.read("$[*]");
	    assertThat(page.size()).isEqualTo(1);
	}

	@Test
	void shouldNotReturnAInfoCollectorWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("sarah1", "abc123")
				.getForEntity("/infocollectors/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
		
	}
			 
	 @Test
	 void shouldReturnASortedPageOfInfoCollector() {
		 ResponseEntity<String> response = restTemplate
				 .withBasicAuth("sarah1", "abc123")
				 .getForEntity("/infocollectors?page=0&size=1&sort=name,desc", String.class);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     JSONArray read = documentContext.read("$[*]");
	     assertThat(read.size()).isEqualTo(3);

	     String name = documentContext.read("$[0].name");
	     assertThat(name).isEqualTo("Pedro");
	     
	 }
	 
	 @Test
	 void shouldReturnASortedPageOfInfoCollectorWithNoParametersAndUseDefaultValues() {
	     ResponseEntity<String> response = restTemplate
	    		 .withBasicAuth("sarah1", "abc123")
	    		 .getForEntity("/infocollectors", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     JSONArray page = documentContext.read("$[*]");
	     assertThat(page.size()).isEqualTo(3);

	     JSONArray name = documentContext.read("$..name");
	     assertThat(name).containsExactlyInAnyOrder("Jose", "Pedro", "Alex");
	   
	 }
	 	 
	 @Test
	 void shouldReturnAllInfoCollectorWhenListIsRequested() {
	     ResponseEntity<String> response = restTemplate
	    		 .withBasicAuth("sarah1", "abc123")
	    		 .getForEntity("/infocollectors", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	 }
	 
	 @Test
	 void shouldReturnAllCashCardsWhenListIsRequested() {
	     ResponseEntity<String> response = restTemplate
	    		 .withBasicAuth("sarah1", "abc123")
	    		 .getForEntity("/infocollectors", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     int cashCardCount = documentContext.read("$.length()");
	     assertThat(cashCardCount).isEqualTo(3);

	     JSONArray ids = documentContext.read("$..id");
	     assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

	     JSONArray name = documentContext.read("$..name");
	     assertThat(name).containsExactlyInAnyOrder("Jose", "Pedro", "Alex");
	     
	     JSONArray surname = documentContext.read("$..surname");
	     assertThat(surname).containsExactlyInAnyOrder("Garcia", "Lopez", "Simarro");
	     
	     JSONArray email = documentContext.read("$..email");
	     assertThat(email).containsExactlyInAnyOrder("josegarcia@gmail.com", "pedrolopez@gmail.com", "alexsimarro@gmail.com");
	
	     JSONArray number = documentContext.read("$..number");
	     assertThat(number).containsExactlyInAnyOrder(640882919, 647532696, 640500399);
	 
	     JSONArray country = documentContext.read("$..country");
	     assertThat(country).containsExactlyInAnyOrder("España", "Portugal", "Macedonia");
	 }

	@Test
	@DirtiesContext
	void shouldCreateANewInfoCollector() {
		InfoCollector newInfoCollector = new InfoCollector(99L, "sarah1", "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España");
		ResponseEntity<Void> createResponse = restTemplate
				.withBasicAuth("sarah1", "abc123")
				.postForEntity("/infocollectors", newInfoCollector, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewInfoCollector = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("sarah1", "abc123")
				.getForEntity(locationOfNewInfoCollector, String.class);
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