import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestJson {
    ClassLoader classLoader = TestJson.class.getClassLoader();

    @DisplayName("Json with Jackson test")
    @Test
    void jsonTest() throws Exception {

        InputStream inputStream = classLoader.getResourceAsStream("Hero.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream));
        assertThat(jsonNode.get("name").asText()).isEqualTo("Molecule Man");
        assertThat(jsonNode.get("age").asInt()).isEqualTo(29);
        assertThat(jsonNode.get("secretIdentity").asText()).isEqualTo("Dan Jukes");

        List<String> list = List.of("Radiation resistance", "Turning tiny", "Radiation blast");
    }
}


