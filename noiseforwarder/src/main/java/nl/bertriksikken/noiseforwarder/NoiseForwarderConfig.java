package nl.bertriksikken.noiseforwarder;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bertriksikken.noiseforwarder.senscom.SensComConfig;
import nl.bertriksikken.noiseforwarder.ttn.TtnConfig;

public final class NoiseForwarderConfig {

    @JsonProperty("ttn")
    private TtnConfig ttnConfig = new TtnConfig();

    @JsonProperty("senscom")
    private SensComConfig sensComConfig = new SensComConfig();
    
    public TtnConfig getTtnConfig() {
        return ttnConfig;
    }
    
    public SensComConfig getSensComConfig() {
        return sensComConfig;
    }

}
