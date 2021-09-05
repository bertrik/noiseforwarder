package nl.bertriksikken.noiseforwarder.senscom;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class SensComConfig {

    @JsonProperty("url")
    private String url = "https://api.sensor.community";

    @JsonProperty("timeout")
    private int timeout = 30;

    public String getUrl() {
        return url;
    }

    public int getTimeout() {
        return timeout;
    }

}
