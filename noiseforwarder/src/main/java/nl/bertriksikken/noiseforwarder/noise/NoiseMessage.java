package nl.bertriksikken.noiseforwarder.noise;

import java.util.Arrays;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Decoded representation of TTN noise message.
 */
public final class NoiseMessage {

    private static final int NUM_OCTAVES = 9;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final double[] la;
    private final double[] lc;
    private final double[] lz;
    private final double[] spectrum;

    private NoiseMessage(double[] la, double[] lc, double[] lz, double[] spectrum) {
        this.la = la;
        this.lc = lc;
        this.lz = lz;
        this.spectrum = spectrum;
    }

    /**
     * Parses a raw byte array into a noise message.
     * 
     * @param data the raw data
     * @return the noise message.
     * @throws NoiseParseException in case the raw data could not be parsed
     */
    public static NoiseMessage parse(byte[] data) throws NoiseParseException {
        if (data.length < 27) {
            throw new NoiseParseException("data too small to parse");
        }

        double[] la = getMinMaxAvg(data, 0);
        double[] lc = getMinMaxAvg(data, 3);
        double[] lz = getMinMaxAvg(data, 6);
        int index = 9;
        double[] spectrum = new double[NUM_OCTAVES];
        for (int i = 0; i < NUM_OCTAVES; i++) {
            spectrum[i] = get12bits(data, index++);
        }
        return new NoiseMessage(la, lc, lz, spectrum);
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
            return new NoiseMessage(n.la.asArray(), n.lc.asArray(), n.lz.asArray(), n.spectrum);
        } catch (JsonProcessingException e) {
            throw new NoiseParseException("Could not deserialize JSON: " + e.getMessage());
        }
    }

    private static double[] getMinMaxAvg(byte[] data, int index) {
        double[] value = new double[3];
        value[0] = get12bits(data, index++);
        value[1] = get12bits(data, index++);
        value[2] = get12bits(data, index++);
        return value;
    }

    private static double get12bits(byte[] data, int index) {
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

    public double[] getLa() {
        return la.clone();
    }

    public double[] getLc() {
        return lc.clone();
    }

    public double[] getLz() {
        return lz.clone();
    }

    public double[] getSpectrum() {
        return spectrum.clone();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{La=%s,Lc=%s,Lz=%s,spectrum=%s}", Arrays.toString(la), Arrays.toString(lc),
                Arrays.toString(lz), Arrays.toString(spectrum));
    }

}
