package nl.bertriksikken.noiseforwarder;

import org.junit.Assert;
import org.junit.Test;

public final class NoiseForwarderTest {

    /**
     * Verifies construction of the noise forwarder and that it can be stopped.
     */
    @Test
    public void testConstructorStop() {
        NoiseForwarderConfig config = new NoiseForwarderConfig();
        NoiseForwarder forwarder = new NoiseForwarder(config);
        forwarder.stop();
        Assert.assertNotNull(forwarder);
    }
    
}
