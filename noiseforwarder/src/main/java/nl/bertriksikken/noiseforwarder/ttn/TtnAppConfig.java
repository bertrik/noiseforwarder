package nl.bertriksikken.noiseforwarder.ttn;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class TtnAppConfig {
    @JsonProperty("name")
    private String name = "noise";

    @JsonProperty("key")
    private String key = "NNSXS.XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.YYYYYY";

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}
