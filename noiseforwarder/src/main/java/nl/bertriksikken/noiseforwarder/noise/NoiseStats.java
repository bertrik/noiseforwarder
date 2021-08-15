package nl.bertriksikken.noiseforwarder.noise;

import java.util.Arrays;
import java.util.Locale;

public final class NoiseStats {

    private double min;
    private double max;
    private double avg;
    private double[] spectrum;

    NoiseStats(double min, double max, double avg, double[] spectrum) {
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.spectrum = spectrum.clone();
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getAvg() {
        return avg;
    }

    public double[] getSpectrum() {
        return spectrum.clone();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{min=%.1f,max=%.1f,avg=%.1f,spectrum=%s}", min, max, avg,
                Arrays.toString(spectrum));
    }

}
