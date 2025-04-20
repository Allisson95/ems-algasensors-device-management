package dev.allisson.algasensors.device.management.api.model;

import io.hypersistence.tsid.TSID;

import java.io.Serializable;

/**
 * DTO for {@link dev.allisson.algasensors.device.management.domain.model.Sensor}
 */
public record SensorOutput(
        TSID id,
        String name,
        String ip,
        String location,
        String protocol,
        String model,
        Boolean enabled
) implements Serializable {
}