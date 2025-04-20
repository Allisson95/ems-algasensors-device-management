package dev.allisson.algasensors.device.management.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity
public class Sensor implements Serializable {
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    @EmbeddedId
    private SensorId id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
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

}
