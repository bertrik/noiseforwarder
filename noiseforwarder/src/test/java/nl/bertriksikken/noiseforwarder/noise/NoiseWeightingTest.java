package nl.bertriksikken.noiseforwarder.noise;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import nl.bertriksikken.noiseforwarder.noise.NoiseWeighting.INoiseWeighting;

public final class NoiseWeightingTest {

    /**
     * Verifies that a flat spectrum of a certain amplitude results in an average of that amplitude, Z-weighting.
     */
    @Test
    public void testZWeightFlat() {
        Map<Double, Double> values = createFlat(2.0);
        NoiseWeighting weighting = new NoiseWeighting();
        INoiseWeighting curve = weighting.getZWeighting();
        double result = weighting.calculatePower(curve, values);
        Assert.assertEquals(2.0, result, 0.0001);
    }
    
    /**
     * Verifies that a flat spectrum of a certain amplitude results in an average of that amplitude, A-weighting.
     */
    @Test
    public void testAWeightFlat() {
        Map<Double, Double> values = createFlat(2.0);
        NoiseWeighting weighting = new NoiseWeighting();
        INoiseWeighting curve = weighting.getAWeighting();
        double result = weighting.calculatePower(curve, values);
        Assert.assertEquals(2.0, result, 0.0001);
    }
    
    /**
     * Verifies the A weighting curve.
     */
    @Test
    public void testAWeighting() {
        NoiseWeighting weighting = new NoiseWeighting();
        INoiseWeighting curve = weighting.getAWeighting();
        assertDb(-26.2, curve.getWeight(63));
        assertDb(-16.1, curve.getWeight(125));
        assertDb(-8.6, curve.getWeight(250));
        assertDb(-3.2, curve.getWeight(500));
        assertDb(0.0, curve.getWeight(1000));
        assertDb(1.2, curve.getWeight(2000));
        assertDb(1.0, curve.getWeight(4000));
        assertDb(-1.1, curve.getWeight(8000));
    }
    
    private void assertDb(double expectedDb, double w) {
        double db = 20.0 * Math.log10(w);
        Assert.assertEquals(expectedDb, db, 0.1);
    }
    
    private Map<Double, Double> createFlat(double p) {
        Map<Double, Double> values = new HashMap<>();
        values.put(62.5, p);
        values.put(125.0, p);
        values.put(250.0, p);
        values.put(500.0, p);
        values.put(1000.0, p);
        values.put(2000.0, p);
        values.put(4000.0, p);
        values.put(8000.0, p);
        return values;
    }
    
}
