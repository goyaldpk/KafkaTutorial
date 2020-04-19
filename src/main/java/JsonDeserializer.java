import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class JsonDeserializer implements Deserializer<JsonNode> {
    private String encoding = "UTF8";
    private ObjectMapper om = new ObjectMapper();

    public JsonDeserializer() {
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("deserializer.encoding");
        }

        if (encodingValue != null && encodingValue instanceof String) {
            this.encoding = (String)encodingValue;
        }

    }

    public JsonNode deserialize(String topic, byte[] data) {
        final ObjectReader reader = om.reader();
        final JsonNode newNode;
        try {
            newNode = reader.readTree(new ByteArrayInputStream(data));
            return newNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
    }
}
