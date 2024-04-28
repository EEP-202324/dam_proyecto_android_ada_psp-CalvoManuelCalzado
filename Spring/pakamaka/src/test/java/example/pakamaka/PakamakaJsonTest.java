package example.pakamaka;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class SerializationTest {

    @Autowired
    private JacksonTester<PakaMaka> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        PakaMaka pakamaka = new PakaMaka(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España");
        assertThat(json.write(pakamaka)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(pakamaka)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(pakamaka)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(pakamaka)).hasJsonPathStringValue("@.name");
        assertThat(json.write(pakamaka)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Jose");
        assertThat(json.write(pakamaka)).hasJsonPathStringValue("@.surname");
        assertThat(json.write(pakamaka)).extractingJsonPathStringValue("@.surname")
                .isEqualTo("Garcia");
        assertThat(json.write(pakamaka)).hasJsonPathStringValue("@.email");
        assertThat(json.write(pakamaka)).extractingJsonPathStringValue("@.email")
                .isEqualTo("josegarcia@gmail.com");
        assertThat(json.write(pakamaka)).hasJsonPathNumberValue("@.number");
        assertThat(json.write(pakamaka)).extractingJsonPathNumberValue("@.number")
                .isEqualTo(640882919);
        assertThat(json.write(pakamaka)).hasJsonPathStringValue("@.Country");
        assertThat(json.write(pakamaka)).extractingJsonPathStringValue("@.Country")
                .isEqualTo("España");

    }
    
    @Test
    void cashCardDeserializationTest() throws IOException {
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
               .isEqualTo(new PakaMaka(99L, "Jose", "Garcia", "josegarcia@gmail.com", 640882919, "España"));
       assertThat(json.parseObject(expected).id()).isEqualTo(99L);
       assertThat(json.parseObject(expected).name()).isEqualTo("Jose");
       assertThat(json.parseObject(expected).surname()).isEqualTo("Garcia");
       assertThat(json.parseObject(expected).email()).isEqualTo("josegarcia@gmail.com");
       assertThat(json.parseObject(expected).number()).isEqualTo(640882919);
       assertThat(json.parseObject(expected).Country()).isEqualTo("España");
    }
}
