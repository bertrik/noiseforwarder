package nl.bertriksikken.noiseforwarder.senscom;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Sensor.community message as uploaded through a POST.
 */
public final class SensComMessage {

    private static final String SOFTWARE_VERSION = "https://github.com/bertrik/noiseforwarder";

    @JsonProperty("software_version")
    private String softwareVersion;

    @JsonProperty("sensordatavalues")
    private final List<SensComItem> items = new ArrayList<>();

    /**
     * Constructor.
     */
    public SensComMessage() {
        this.softwareVersion = SOFTWARE_VERSION;
    }

    public void addItem(String name, String value) {
        items.add(new SensComItem(name, value));
    }

    public void addItem(String name, Double value) {
        addItem(name, String.format(Locale.ROOT, "%.1f", value));
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{softwareVersion=%s,items=%s}", softwareVersion, items);
    }

    private static final class SensComItem {

        @JsonProperty("value_type")
        private String name;
        @JsonProperty("value")
        private String value;

        /**
         * Constructor.
         * 
         * @param name  the item name
         * @param value the item value
         */
        SensComItem(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format(Locale.ROOT, "{name=%s,value=%s}", name, value);
        }

    }
}
