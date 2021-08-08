package nl.bertriksikken.noiseforwarder.noise;

import org.junit.Assert;
import org.junit.Test;

public final class NoiseMessageTest {

    @Test
    public void testParse() throws NoiseParseException {
        byte[] data = {0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x00,
                0x11, 0x22, 0x33};
                
        NoiseMessage message = NoiseMessage.parse(data);
        
        Assert.assertArrayEquals(new double[] {27.4, 56.3, 109.3}, message.getLa(), 0.1);
    }
    
}
