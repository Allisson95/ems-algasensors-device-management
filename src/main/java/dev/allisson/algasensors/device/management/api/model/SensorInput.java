package dev.allisson.algasensors.device.management.api.model;

import java.io.Serializable;

/**
 * DTO for {@link dev.allisson.algasensors.device.management.domain.model.Sensor}
 */
public record SensorInput(
        String name,
        String ip,
        String location,
        String protocol,
        String model
) implements Serializable {
}
