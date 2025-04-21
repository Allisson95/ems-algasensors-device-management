package dev.allisson.algasensors.device.management.api.model;

import java.io.Serializable;

/**
 * DTO for {@link dev.allisson.algasensors.device.management.domain.model.Sensor}
 */
public record SensorDetailOutput(
        SensorOutput sensor,
        SensorMonitoringOutput monitoring
) implements Serializable {
}