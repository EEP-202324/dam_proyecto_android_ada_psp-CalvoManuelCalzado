package com.eep.manuel.Aviones;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AvionesdApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAAvionesWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/Aviones/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);
        
        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("Juan");
        
        String apellido = documentContext.read("$.apellido");
        assertThat(apellido).isEqualTo("Perez");
        
        String DE = documentContext.read("$.AE");
        assertThat(DE).isEqualTo("Boeing 747");
        
    }
    @Test
    void shouldNotReturnAAvionesdWithAnUnknownId() {
      ResponseEntity<String> response = restTemplate.getForEntity("/Aviones/1000", String.class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(response.getBody()).isBlank();
      
    }
    @Test
    @DirtiesContext
    void shouldCreateANewAvion() {
    	Aviones newAviones = new Aviones(2,"Juan","Perez","Boeing 747");  
       ResponseEntity<Void> createResponse = restTemplate.postForEntity("/Aviones", newAviones, Void.class);
       assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
       URI locationOfNewAviones = createResponse.getHeaders().getLocation();
       ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewAviones, String.class);
       assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
      

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isEqualTo(2);
    
    String name = documentContext.read("$.name");
    assertThat(name).isEqualTo("Juan");
    
    String apellido = documentContext.read("$.apellido");
    assertThat(apellido).isEqualTo("Perez");
    
    String DE = documentContext.read("$.AE");
    assertThat(DE).isEqualTo("Boeing 747");
    
    }
    @Test
    void shouldReturnAllAvionesWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/Aviones", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int avionesCount = documentContext.read("$.length()");
        assertThat(avionesCount).isEqualTo(3);

        JSONArray id = documentContext.read("$..id");
        assertThat(id).containsExactlyInAnyOrder(1, 2, 3);

        JSONArray name = documentContext.read("$..name");
        assertThat(name).containsExactlyInAnyOrder("Juan","Pablo","Roberto");
        
        JSONArray apellido = documentContext.read("$..apellido");
        assertThat(apellido).containsExactlyInAnyOrder("Perez","Posada","Lopez");
        
        JSONArray DE = documentContext.read("$..AE");
        assertThat(DE).containsExactlyInAnyOrder("Boeing 747","Airbus A380","B-52");
    }
    @Test
    void shouldReturnAPageOfAviones() {
        ResponseEntity<String> response = restTemplate.getForEntity("/Aviones?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }
    @Test
    void shouldReturnASortedPageOfAviones() {
        ResponseEntity<String> response = restTemplate.getForEntity("/Aviones?page=0&size=1&sort=id,asc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray read = documentContext.read("$[*]");
        assertThat(read.size()).isEqualTo(1);

        int id = documentContext.read("$[0].id");
        assertThat(id).isEqualTo(1);
    }
    @Test
    void shouldReturnASortedPageOfAvionesWithNoParametersAndUseDefaultValues() {
        ResponseEntity<String> response = restTemplate.getForEntity("/Aviones", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);

        JSONArray id = documentContext.read("$..id");
        assertThat(id).containsExactlyInAnyOrder(1, 2, 3);

        JSONArray name = documentContext.read("$..name");
        assertThat(name).containsExactlyInAnyOrder("Juan","Pablo","Roberto");
        
        JSONArray apellido = documentContext.read("$..apellido");
        assertThat(apellido).containsExactlyInAnyOrder("Perez","Posada","Lopez");
        
        JSONArray DE = documentContext.read("$..AE");
        assertThat(DE).containsExactlyInAnyOrder("Boeing 747","Airbus A380","B-52");
    }
    @Test
    @DirtiesContext
    void shouldUpdateAnExistingAviones() {
        // Define los valores de ejemplo para la actualización
    	Aviones avionesUpdate = new Aviones(1, "Juan", "Perez", "Boeing 747");

        // Realiza la solicitud PUT para actualizar el objeto Aviones
        HttpEntity<Aviones> request = new HttpEntity<>(avionesUpdate);
        ResponseEntity<Void> response = restTemplate
                .exchange("/Aviones/1", HttpMethod.PUT, request, Void.class);

        // Verifica que la actualización fue exitosa

        // Verifica que los campos actualizados sean los esperados
        ResponseEntity<Aviones> getResponse = restTemplate
                .getForEntity("/Aviones/1", Aviones.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Aviones updatedAviones = getResponse.getBody();
        assertThat(updatedAviones.getId()).isEqualTo(1);
        assertThat(updatedAviones.getName()).isEqualTo("Juan");
        assertThat(updatedAviones.getApellido()).isEqualTo("Perez");
        assertThat(updatedAviones.getAE()).isEqualTo("Boeing 747");

    }
    
}

