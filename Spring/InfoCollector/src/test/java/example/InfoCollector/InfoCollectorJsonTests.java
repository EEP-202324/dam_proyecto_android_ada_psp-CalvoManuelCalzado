package example.InfoCollector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class InfoCollectorJsonTests {

    @Autowired
    private JacksonTester<InfoCollector> json;

    @Test
    void infoCollectorSerializationTest() throws IOException {
        InfoCollector infoCollector = new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España");
        assertThat(json.write(infoCollector)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(infoCollector)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(infoCollector)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.name");
        assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Jose");
        assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.surname");
        assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.surname")
                .isEqualTo("Garcia");
        assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.email");
        assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.email")
                .isEqualTo("josegarcia@gmail.com");
        assertThat(json.write(infoCollector)).hasJsonPathNumberValue("@.number");
        assertThat(json.write(infoCollector)).extractingJsonPathNumberValue("@.number")
                .isEqualTo(640882919);
        assertThat(json.write(infoCollector)).hasJsonPathStringValue("@.Country");
        assertThat(json.write(infoCollector)).extractingJsonPathStringValue("@.Country")
                .isEqualTo("España");
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
               "Country": "España"
           }
           """;
       assertThat(json.parse(expected))
               .isEqualTo(new InfoCollector(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España"));
       assertThat(json.parseObject(expected).id()).isEqualTo(99);
       assertThat(json.parseObject(expected).name()).isEqualTo("Jose");
       assertThat(json.parseObject(expected).surname()).isEqualTo("Garcia");
       assertThat(json.parseObject(expected).email()).isEqualTo("josegarcia@gmail.com");
       assertThat(json.parseObject(expected).number()).isEqualTo(640882919);
       assertThat(json.parseObject(expected).Country()).isEqualTo("España");
    }
}