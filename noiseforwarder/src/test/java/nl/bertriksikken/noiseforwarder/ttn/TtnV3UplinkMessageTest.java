package nl.bertriksikken.noiseforwarder.ttn;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests related to TTN messages.
 */
public final class TtnV3UplinkMessageTest {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Reads an example message and verifies parsing.
     * 
     * @throws IOException
     */
    @Test
    public void testDecode() throws IOException {
        // decode JSON
        Ttnv3UplinkMessage ttnv3UplinkMessage;
        try (InputStream is = this.getClass().getResourceAsStream("/ttnv3_mqtt_message.json")) {
	        ttnv3UplinkMessage = mapper.readValue(is, Ttnv3UplinkMessage.class);
        }
        
        TtnUplinkMessage message = ttnv3UplinkMessage.toTtnUplinkMessage(); 
        Assert.assertEquals("0000547AF1BF713C", message.getDevEui());
        Assert.assertEquals(19, message.getRawPayload().length);
        Assert.assertEquals(1, message.getPort());
        Assert.assertEquals(7, message.getSF());
	}
    
    /**
     * Verifies parsing of TTN data with "decoded_payload" present.
     */
    @Test
    public void testDecodeWithFields() throws IOException {
        // decode JSON
        Ttnv3UplinkMessage ttnv3UplinkMessage;
        try (InputStream is = this.getClass().getResourceAsStream("/ttnv3_mqtt_message_with_fields.json")) {
            ttnv3UplinkMessage = mapper.readValue(is, Ttnv3UplinkMessage.class);
        }
        TtnUplinkMessage message = ttnv3UplinkMessage.toTtnUplinkMessage();
        String decodedFields = message.getDecodedFields();
        JsonNode node = mapper.readTree(decodedFields);
        Assert.assertEquals(35.5, node.at("/la/avg").asDouble(), 0.1);
    }

}
