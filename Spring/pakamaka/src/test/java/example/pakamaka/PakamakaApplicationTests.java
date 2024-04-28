package example.pakamaka;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PakamakaApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/pakamakas/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(99);
//        String name = documentContext.read("$.name");
//        assertThat(name).isEqualTo("Jose");
//        String surname = documentContext.read("$.surname");
//        assertThat(surname).isEqualTo("Garcia");
//        String email = documentContext.read("$.email");
//        assertThat(email).isEqualTo("josegarcia@gmail.com");
//        int numero = documentContext.read("$.number");
//        assertThat(numero).isEqualTo(640882919);
//        String Country = documentContext.read("$.COuntry");
//        assertThat(Country).isEqualTo("España");

    }
}