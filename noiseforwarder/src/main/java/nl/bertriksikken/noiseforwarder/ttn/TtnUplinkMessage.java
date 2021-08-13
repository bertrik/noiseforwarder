package nl.bertriksikken.noiseforwarder.ttn;

import java.util.Locale;

import nl.bertriksikken.noiseforwarder.util.HexConverter;

/**
 * Common class containing only the fields from the TTN upload message relevant
 * to us.
 */
public final class TtnUplinkMessage {

    private final String appId;
    private final String devId;
    private final String devEui;
    private final byte[] rawPayload;
    private final String decodedPayload;
    private final int port;
    private double rssi = Double.NaN;
    private double snr = Double.NaN;
    private int sf = 0;

    public TtnUplinkMessage(String appId, String devId, String devEui, byte[] rawPayload, String decodedPayload,
            int port) {
        this.appId = appId;
        this.devId = devId;
        this.devEui = devEui;
        this.rawPayload = rawPayload.clone();
        this.decodedPayload = decodedPayload;
        this.port = port;
    }

    public void setRadioParams(double rssi, double snr, int sf) {
        this.rssi = rssi;
        this.snr = snr;
        this.sf = sf;
    }

    public String getAppId() {
        return appId;
    }

    public String getDevId() {
        return devId;
    }

    public String getDevEui() {
        return devEui;
    }

    public byte[] getRawPayload() {
        return rawPayload.clone();
    }

    public String getDecodedPayload() {
        return decodedPayload;
    }

    public int getPort() {
        return port;
    }

    public double getRSSI() {
        return rssi;
    }

    public double getSNR() {
        return snr;
    }

    public int getSF() {
        return sf;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "EUI %s, data %s, decoded %s, port %d, SF %d", devEui,
                HexConverter.toString(rawPayload), decodedPayload, port, sf);
    }

}
