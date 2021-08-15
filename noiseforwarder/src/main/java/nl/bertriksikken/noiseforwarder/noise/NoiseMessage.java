package nl.bertriksikken.noiseforwarder.noise;

import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Decoded representation of TTN noise message.
 */
public final class NoiseMessage {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final NoiseStats la;
    private final NoiseStats lc;
    private final NoiseStats lz;

    private NoiseMessage(NoiseStats la, NoiseStats lc, NoiseStats lz) {
        this.la = la;
        this.lc = lc;
        this.lz = lz;
    }

    /**
     * Parses a JSON string into a noise message.
     * 
     * @param json the JSON string
     * @return the noise message
     * @throws NoiseParseException in case the JSON could not be parsed 
     */
    public static NoiseMessage parse(String json) throws NoiseParseException {
        try {
            NoiseJson n = MAPPER.readValue(json, NoiseJson.class);
            NoiseStats la = new NoiseStats(n.la.min, n.la.max, n.la.avg, n.la.spectrum);
            NoiseStats lc = new NoiseStats(n.lc.min, n.lc.max, n.lc.avg, n.lc.spectrum);
            NoiseStats lz = new NoiseStats(n.lz.min, n.lz.max, n.lz.avg, n.lz.spectrum);
            return new NoiseMessage(la, lc, lz);
        } catch (JsonProcessingException e) {
            throw new NoiseParseException("Could not deserialize JSON: " + e.getMessage());
        }
    }

    public static double get12bits(byte[] data, int index) {
        int byteIndex = 3 * index / 2;
        int b0 = data[byteIndex++];
        int b1 = data[byteIndex];
        int value;
        if ((index % 2) == 0) {
            b0 &= 0xFF;
            b1 &= 0xF0;
            value = (b0 << 4) | (b1 >> 4);
        } else {
            b0 &= 0x0F;
            b1 &= 0xFF;
            value = (b0 << 8) | b1;
        }
        return value / 10.0;
    }

    public NoiseStats getLa() {
        return la;
    }

    public NoiseStats getLc() {
        return lc;
    }

    public NoiseStats getLz() {
        return lz;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{La=%s,Lc=%s,Lz=%s}", la, lc, lz);
    }

}
