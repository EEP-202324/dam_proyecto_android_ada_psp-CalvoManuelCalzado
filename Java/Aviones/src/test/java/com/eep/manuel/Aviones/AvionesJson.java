package com.eep.manuel.Aviones;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest

class AvionesJsonTest {

	@Autowired
	private JacksonTester<Aviones> json;
	@Autowired
	private JacksonTester<Aviones[]> jsonlist;

	private Aviones[] Aviones;

	@BeforeEach
	void setUp() {
		Aviones = Arrays.array(new Aviones(1, "Juan", "Perez", "Boeing 747"),
				new Aviones(2, "Pablo", "Posada", "Airbus A380"), new Aviones(3, "Roberto", "Lopez", "B-52"));
	}

	@Test
	void AvionesSerializationTest() throws IOException {
		Aviones Avion = new Aviones(1, "Juan", "Perez", "Boeing 747");
		assertThat(json.write(Avion)).isStrictlyEqualToJson("expected.json");

		assertThat(json.write(Avion)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(Avion)).extractingJsonPathNumberValue("@.id").isEqualTo(1);

		assertThat(json.write(Avion)).hasJsonPathStringValue("@.name");
		assertThat(json.write(Avion)).extractingJsonPathStringValue("@.name").isEqualTo("Juan");

		assertThat(json.write(Avion)).hasJsonPathStringValue("@.apellido");
		assertThat(json.write(Avion)).extractingJsonPathStringValue("@.apellido").isEqualTo("Perez");

		assertThat(json.write(Avion)).hasJsonPathStringValue("@.ae");
		assertThat(json.write(Avion)).extractingJsonPathStringValue("@.ae").isEqualTo("Boeing 747");
	}

	@Test
	void ListDeserializationTest() throws IOException {
		assertThat(jsonlist.write(Aviones)).isStrictlyEqualToJson("list.json");
	}

	@Test
	void AvionesDeserializationTest() throws IOException {
	    String expected = """
	            {
	              "id": 1,
	              "name": "Pablo",
	              "apellido": "Posada",
	              "ae": "Airbus A380"
	            }
	             """;
	    Aviones expectedAvion = new Aviones(1, "Pablo", "Posada", "Airbus A380");
	    assertThat(json.parse(expected)).isEqualTo(expectedAvion);
	    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
	    assertThat(json.parseObject(expected).getName()).isEqualTo("Pablo");
	    assertThat(json.parseObject(expected).getApellido()).isEqualTo("Posada");
	    assertThat(json.parseObject(expected).getAE()).isEqualTo("Airbus A380");
	}


	@Test
	void internsListDeserializationTest() throws IOException {
		String expected = """
				[
				    { "id":1, "name":"Juan", "apellido":"Perez", "ae":"Boeing 747" },
				    { "id":2, "name":"Pablo", "apellido":"Posada", "ae":"Airbus A380" },
				    { "id":3, "name":"Roberto", "apellido":"Lopez", "ae":"B-52" }
				]
				 """;
		assertThat(jsonlist.parse(expected)).isEqualTo(Aviones);
	}

}
