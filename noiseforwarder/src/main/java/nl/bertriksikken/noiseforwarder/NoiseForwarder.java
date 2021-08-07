package nl.bertriksikken.noiseforwarder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import nl.bertriksikken.noiseforwarder.senscom.SensComConfig;
import nl.bertriksikken.noiseforwarder.senscom.SensComUploader;
import nl.bertriksikken.noiseforwarder.ttn.MqttListener;
import nl.bertriksikken.noiseforwarder.ttn.TtnAppConfig;
import nl.bertriksikken.noiseforwarder.ttn.TtnConfig;
import nl.bertriksikken.noiseforwarder.ttn.TtnUplinkMessage;

/**
 * Main applicaton point for the noise forwarder.
 */
public final class NoiseForwarder {

    private static final Logger LOG = LoggerFactory.getLogger(NoiseForwarder.class);
    private static final String CONFIG_FILE = "noiseforwarder.yaml";

    private final List<MqttListener> mqttListeners = new ArrayList<>();
    private final SensComUploader sensComUploader;

    public static void main(String[] args) throws IOException, MqttException {
        PropertyConfigurator.configure("log4j.properties");

        NoiseForwarderConfig config = readConfig(new File(CONFIG_FILE));
        NoiseForwarder forwarder = new NoiseForwarder(config);
        forwarder.start();
        Runtime.getRuntime().addShutdownHook(new Thread(forwarder::stop));
    }

    private NoiseForwarder(NoiseForwarderConfig config) throws IOException {
        SensComConfig sensComConfig = config.getSensComConfig();
        sensComUploader = SensComUploader.create(sensComConfig);

        TtnConfig ttnConfig = config.getTtnConfig();
        for (TtnAppConfig appConfig : config.getTtnConfig().getApps()) {
            // add listener for each app
            LOG.info("Adding MQTT listener for TTN application '{}'", appConfig.getName());
            MqttListener listener = new MqttListener(ttnConfig, appConfig, uplink -> messageReceived(uplink));
            mqttListeners.add(listener);
        }
    }

    private static NoiseForwarderConfig readConfig(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (FileInputStream fis = new FileInputStream(file)) {
            return mapper.readValue(fis, NoiseForwarderConfig.class);
        } catch (IOException e) {
            LOG.warn("Failed to load config {}, writing defaults", file.getAbsoluteFile());
            NoiseForwarderConfig config = new NoiseForwarderConfig();
            mapper.writeValue(file, config);
            return config;
        }
    }

    /**
     * Starts the application.
     * 
     * @throws MqttException in case of a problem starting MQTT client
     * @throws IOException
     */
    private void start() throws MqttException, IOException {
        LOG.info("Starting noiseforwarder application");

        sensComUploader.start();
        for (MqttListener listener : mqttListeners) {
            listener.start();
        }

        LOG.info("Started noiseforwarder application");
    }

    /**
     * Stops the application.
     * 
     * @throws MqttException
     */
    private void stop() {
        LOG.info("Stopping noiseforwarder application");

        mqttListeners.forEach(MqttListener::stop);
        sensComUploader.stop();

        LOG.info("Stopped noiseforwarder application");
    }

    private void messageReceived(TtnUplinkMessage uplink) {
        // TODO Auto-generated method stub
    }

}
