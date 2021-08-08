package nl.bertriksikken.noiseforwarder.noise;

public class NoiseMessage {

    private static final int NUM_OCTAVES = 9;
    private double[] la;
    private double[] lc;
    private double[] lz;
    private double[] spectrum = new double[NUM_OCTAVES];
    
    private NoiseMessage(double[] la, double[] lc, double[] lz, double[] spectrum) {
        this.la = la;
        this.lc = lc;
        this.lz = lz;
        this.spectrum = spectrum;
    }
    
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
        return 0.1 * value;
    }

    public static int getNumOctaves() {
        return NUM_OCTAVES;
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

}

