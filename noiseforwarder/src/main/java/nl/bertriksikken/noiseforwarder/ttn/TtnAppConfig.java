package nl.bertriksikken.noiseforwarder.ttn;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class TtnAppConfig {
    @JsonProperty("name")
    private String name = "particulatematter";

    @JsonProperty("key")
    private String key = "NNSXS.LHD22PFZMI3B7WF6FDWIK45N4244U7DWRVWZASI.XXXXXX";

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}
