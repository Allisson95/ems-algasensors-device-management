package dev.allisson.algasensors.device.management.api.model;

import io.hypersistence.tsid.TSID;

import java.io.Serializable;
import java.time.Instant;

public record SensorMonitoringOutput(
        TSID id,
        Double lastTemperature,
        Instant updatedAt,
        Boolean enabled
) implements Serializable {
}