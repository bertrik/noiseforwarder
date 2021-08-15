package nl.bertriksikken.noiseforwarder.noise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
final class NoiseJson {

    @JsonProperty("la")
    NoiseStatsJson la = new NoiseStatsJson();

    @JsonProperty("lc")
    NoiseStatsJson lc = new NoiseStatsJson();

    @JsonProperty("lz")
    NoiseStatsJson lz = new NoiseStatsJson();

    static final class NoiseStatsJson {

        @JsonProperty("min")
        double min = 0.0;
        @JsonProperty("max")
        double max = 0.0;
        @JsonProperty("avg")
        double avg = 0.0;
        @JsonProperty("spectrum")
        double[] spectrum = new double[0];
    }

}
