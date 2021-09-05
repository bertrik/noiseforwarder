package nl.bertriksikken.noiseforwarder.noise;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public final class NoiseMessageTest {

    /**
     * Verifies parsing from JSON data.
     */
    @Test
    public void testParseJson() throws NoiseParseException, IOException, URISyntaxException {
        String json = new String(Files.readAllBytes(Paths.get(getClass().getResource("/noise.json").toURI())),
                StandardCharsets.UTF_8);
        NoiseMessage noiseMessage = NoiseMessage.parse(json);
        Assert.assertEquals(33.9, noiseMessage.getLa().getMin(), 0.1);
    }
    
    /**
     * Tests formatting.
     */
    @Test
    public void testFormat() throws NoiseParseException, IOException, URISyntaxException {
        String json = new String(Files.readAllBytes(Paths.get(getClass().getResource("/noise.json").toURI())),
                StandardCharsets.UTF_8);
        NoiseMessage noiseMessage = NoiseMessage.parse(json);
        String formatted = noiseMessage.toString();
        System.out.println(formatted);
    }

}
