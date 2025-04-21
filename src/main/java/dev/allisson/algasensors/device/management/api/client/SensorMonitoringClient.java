package dev.allisson.algasensors.device.management.api.client;

import dev.allisson.algasensors.device.management.domain.model.SensorId;

public interface SensorMonitoringClient {
    void enableMonitoring(SensorId sensorId);

    void disableMonitoring(SensorId sensorId);
}
