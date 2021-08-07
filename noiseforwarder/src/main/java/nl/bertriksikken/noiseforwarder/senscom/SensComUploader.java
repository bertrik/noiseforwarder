package nl.bertriksikken.noiseforwarder.senscom;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Uploader for sensor.community
 */
public final class SensComUploader {

    private static final Logger LOG = LoggerFactory.getLogger(SensComUploader.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final ISensComApi restClient;
    private final ExecutorService executor;
    
    private static final String PIN = "15";

    /**
     * Constructor.
     * 
     * @param restClient the REST client
     */
    SensComUploader(ISensComApi restClient) {
        this.restClient = restClient;
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Creates a new REST client.
     */
    public static SensComUploader create(SensComConfig config) {
        LOG.info("Creating new REST client for '{}' with timeout {}", config.getUrl(), config.getTimeout());
        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(Duration.ofSeconds(config.getTimeout()))
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(config.getUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).client(client).build();
        ISensComApi restClient = retrofit.create(ISensComApi.class);
        return new SensComUploader(restClient);
    }

    private void uploadMeasurement(String sensorId, String pin, SensComMessage sensComMessage) {
        try {
            LOG.info("Sending for {} to pin {}: '{}'", sensorId, pin, mapper.writeValueAsString(sensComMessage));
            Response<String> response = restClient.pushSensorData(pin, sensorId, sensComMessage).execute();
            if (response.isSuccessful()) {
                LOG.info("Result success: {}", response.body());
            } else {
                LOG.warn("Request failed: {}", response.message());
            }
        } catch (Exception e) {
            LOG.trace("Caught exception", e);
            LOG.warn("Caught exception: {}", e.getMessage());
        }
    }

    public void scheduleUpload(String sensorId, SensComMessage message) {
        executor.execute(() -> uploadMeasurement(sensorId, PIN, message));
    }

    public void start() {
        LOG.info("Starting sensor.community uploader");
    }

    public void stop() {
        LOG.info("Stopping sensor.community uploader");
        executor.shutdown();
    }

}
