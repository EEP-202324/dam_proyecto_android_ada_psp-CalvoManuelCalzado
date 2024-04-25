package example.pakamaka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PakamakaJsonTest {

    @Autowired
    private JacksonTester<PakaMaka> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        PakaMaka pakaMaka = new PakaMaka(99L, 123.45);
        assertThat(json.write(pakaMaka)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(pakaMaka)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(pakaMaka)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(pakaMaka)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(pakaMaka)).extractingJsonPathNumberValue("@.amount")
             .isEqualTo(123.45);
    }
}