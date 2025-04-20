package dev.allisson.algasensors.device.management.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Entity
public class Sensor implements Serializable {
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    @EmbeddedId
    private SensorId id;

    @Setter
    private String name;

    @Setter
    private String ip;

    @Setter
    private String location;

    @Setter
    private String protocol;

    @Setter
    private String model;

    private Boolean enabled;

    protected Sensor() {
        // Default constructor for JPA
    }

    public Sensor(String name, String ip, String location, String protocol, String model) {
        this.id = new SensorId();
        this.name = name;
        this.ip = ip;
        this.location = location;
        this.protocol = protocol;
        this.model = model;
        this.enabled = false;
    }

    public void enable() {
        if (this.enabled) {
            throw new IllegalStateException("Sensor is already enabled");
        }
        this.enabled = true;
    }

    public void disable() {
        if (!this.enabled) {
            throw new IllegalStateException("Sensor is already disabled");
        }
        this.enabled = false;
    }
}
