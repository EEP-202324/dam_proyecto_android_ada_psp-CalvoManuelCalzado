package example.InfoCollector;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class InfoCollectorJsonTests {

	@Autowired
	private JacksonTester<InfoCollector> json;

	@Autowired
	private JacksonTester<InfoCollector[]> jsonList;

	private InfoCollector[] infoCollectors;

	@BeforeEach
	void setUp() {
		infoCollectors = Arrays.array(
				new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España"),
				new InfoCollector(100L, "Pedro", "Lopez", "pedrolopez@gmail.com", 647532696, "Portugal"),
				new InfoCollector(101L, "Alex", "Simarro", "alexsimarro@gmail.com", 640500399, "Macedonia"));
	}

	@Test
	void infoCollectorSerializationTest() throws IOException {

		InfoCollector infoCollector = new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919,
				"España");
		assertThat(json.write(infoCollector)).isStrictlyEqualToJson("expected.json");
		assertThat(json.write(infoCollector)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(infoCollector)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
		assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.name");
		assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.name").isEqualTo("Jose");
		assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.surname");
		assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.surname").isEqualTo("Garcia");
		assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.email");
		assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.email")
				.isEqualTo("josegarcia@gmail.com");
		assertThat(json.write(infoCollector)).hasJsonPathNumberValue("@.number");
		assertThat(json.write(infoCollector)).extractingJsonPathNumberValue("@.number").isEqualTo(640882919);
		assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.country");
		assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.country").isEqualTo("España");
		assertThat(jsonList.write(infoCollectors)).isStrictlyEqualToJson("list.json");
	}

	@Test
	void infoCollectorListDeserializationTest() throws IOException {
		String expected = """
				   	[
				   	  {"id": 99, "name": "Jose", "surname": "Garcia","email": "josegarcia@gmail.com", "number": 640882919, "country": "España" },
				   	  {"id": 100, "name": "Pedro", "surname": "Lopez","email": "pedrolopez@gmail.com","number": 647532696, "country": "Portugal" },
				   	  {"id": 101, "name": "Alex", "surname": "Simarro","email": "alexsimarro@gmail.com","number": 640500399, "country": "Macedonia" }
				   	]
				""";
		assertThat(jsonList.parse(expected)).isEqualTo(infoCollectors);
	}

	@Test
	void infoCollectorDeserializationTest() throws IOException {
		String expected = """
				    {
				    "id":99,
				    "name": "Jose",
				    "surname": "Garcia",
				    "email": "josegarcia@gmail.com",
				    "number": 640882919,
				    "country": "España"
				}
				""";
		assertThat(json.parse(expected))
				.isEqualTo(new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España"));
		assertThat(json.parseObject(expected).id()).isEqualTo(99);
		assertThat(json.parseObject(expected).name()).isEqualTo("Jose");
		assertThat(json.parseObject(expected).surname()).isEqualTo("Garcia");
		assertThat(json.parseObject(expected).email()).isEqualTo("josegarcia@gmail.com");
		assertThat(json.parseObject(expected).number()).isEqualTo(640882919);
		assertThat(json.parseObject(expected).country()).isEqualTo("España");
	}

}