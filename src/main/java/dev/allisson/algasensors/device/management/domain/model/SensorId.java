package dev.allisson.algasensors.device.management.domain.model;

import dev.allisson.algasensors.device.management.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Embeddable
public record SensorId(TSID value) implements Serializable {

    public SensorId {
        if (value == null) {
            throw new IllegalArgumentException("SensorId cannot be null");
        }
    }

    public SensorId(String value) {
        this(TSID.from(value));
    }

    public SensorId(long value) {
        this(TSID.from(value));
    }

    public SensorId() {
        this(IdGenerator.generateId());
    }

    @Override
    public @NonNull String toString() {
        return value.toString();
    }

}
