package nl.bertriksikken.noiseforwarder.noise;

import java.util.Map;
import java.util.Map.Entry;

/**
 */
public final class NoiseWeighting {
    
    private final double NORM_A;

    public NoiseWeighting() {
        NORM_A = ra(1000.0);
    }

    /**
     * Non-normalized A-curve amplitude weighting, @see https://en.wikipedia.org/wiki/A-weighting .
     * 
     * @param f frequency (Hz)
     * @return the non-normalized amplitude weight
     */
    private double ra(double f) {
        double f2 = f * f;
        double term0 = Math.pow(12194 * f2, 2);
        double term1 = f2 + Math.pow(20.6, 2);
        double term2 = Math.sqrt(f2 + Math.pow(107.7, 2));
        double term3 = Math.sqrt(f2 + Math.pow(737.9, 2));
        double term4 = f2 + Math.pow(12194, 2);
        return term0 / (term1 * term2 * term3 * term4);
    }
    
    /**
     * @return normalized A weight curve (amplitude)
     */
    public INoiseWeighting getAWeighting() {
        return f -> ra(f) / NORM_A;
    }
    
    /**
     * @return normalized Z weight curve (amplitude)
     */
    public INoiseWeighting getZWeighting() {
        return f -> 1.0;
    }
    
    /**
     * Calculates a weighted average.
     * 
     * @param weighting the weighting function
     * @param values pairs of (frequency,power)
     * @return the weighted average
     */
    public double calculatePower(INoiseWeighting weighting, Map<Double, Double> values) {
        double totalWeight = 0.0;
        double totalSum = 0.0;
        for (Entry<Double, Double> entry : values.entrySet()) {
            double f = entry.getKey();
            double p = entry.getValue();
            double w = weighting.getWeight(f);
            double wp = w * w;
            totalSum += wp * p;
            totalWeight += wp;
        }
        return totalSum / totalWeight;
    }
    
    // weighing function, normalized at 1 KHz, "amplitude weight"
    public interface INoiseWeighting {
        double getWeight(double frequency);
    }
    
}
