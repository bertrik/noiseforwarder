package nl.bertriksikken.noiseforwarder.noise;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public final class NoiseMessageTest {

    /**
     * Verifies parsing from raw byte array.
     */
    @Test
    public void testParseRaw() throws NoiseParseException {
        byte[] data = {0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33};
                
        NoiseMessage message = NoiseMessage.parse(data);
        Assert.assertArrayEquals(new double[] {27.4, 56.3, 109.3}, message.getLa(), 0.1);
    }

    /**
     * Verifies parsing from JSON data.
     */
    @Test
    public void testParseJson() throws NoiseParseException, IOException, URISyntaxException {
        String json = new String(Files.readAllBytes(Paths.get(getClass().getResource("/noise.json").toURI())));
        NoiseMessage noiseMessage = NoiseMessage.parse(json);
        Assert.assertArrayEquals(new double[] {1.0, 2.0, 3.0}, noiseMessage.getLa(), 0.1);
    }

}
