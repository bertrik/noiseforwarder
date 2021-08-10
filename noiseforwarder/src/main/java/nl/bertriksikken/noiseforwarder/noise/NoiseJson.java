package nl.bertriksikken.noiseforwarder.noise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
final class NoiseJson {

    @JsonProperty("la")
    MinMaxAvg la = new MinMaxAvg();

    @JsonProperty("lc")
    MinMaxAvg lc = new MinMaxAvg();

    @JsonProperty("lz")
    MinMaxAvg lz = new MinMaxAvg();

    @JsonProperty("spectrum")
    double[] spectrum = new double[0];
    
    static final class MinMaxAvg {

        @JsonProperty("min")
        double min = 0.0;
        @JsonProperty("max")
        double max = 0.0;
        @JsonProperty("avg")
        double avg = 0.0;

        double[] asArray() {
            return new double[] { min, max, avg };
        }
    }

}
