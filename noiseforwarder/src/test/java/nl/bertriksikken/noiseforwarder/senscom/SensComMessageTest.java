package nl.bertriksikken.noiseforwarder.senscom;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class SensComMessageTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testFormat() throws JsonProcessingException {
        SensComMessage message = new SensComMessage();
        message.addItem("name", 17.4);
        String json = mapper.writeValueAsString(message);
        Assert.assertEquals(
                "{\"software_version\":\"https://github.com/bertrik/noiseforwarder\","
                + "\"sensordatavalues\":[{\"value_type\":\"name\",\"value\":\"17.4\"}]}",
                json);
    }

}
