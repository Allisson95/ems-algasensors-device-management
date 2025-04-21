package dev.allisson.algasensors.device.management.api.client.impl;

import dev.allisson.algasensors.device.management.api.client.SensorMonitoringClient;
import dev.allisson.algasensors.device.management.domain.model.SensorId;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {
    private final RestClient restClient;

    public SensorMonitoringClientImpl(final RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("http://localhost:8082").build();
    }

    @Override
    public void enableMonitoring(final SensorId sensorId) {
        this.restClient.put()
                .uri("api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(final SensorId sensorId) {
        this.restClient.delete()
                .uri("api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }
}
