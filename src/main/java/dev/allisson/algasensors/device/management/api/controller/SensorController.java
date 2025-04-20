package dev.allisson.algasensors.device.management.api.controller;

import dev.allisson.algasensors.device.management.api.mapper.SensorMapper;
import dev.allisson.algasensors.device.management.api.model.SensorInput;
import dev.allisson.algasensors.device.management.api.model.SensorOutput;
import dev.allisson.algasensors.device.management.domain.model.Sensor;
import dev.allisson.algasensors.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;

    @PostMapping
    public ResponseEntity<SensorOutput> create(@RequestBody final SensorInput input) {
        log.info("Received a new request to create a sensor: {}", input);
        final Sensor sensor = this.sensorMapper.toEntity(input);

        this.sensorRepository.persist(sensor);

        return ResponseEntity
                .created(URI.create("/api/sensors/" + sensor.getId()))
                .body(this.sensorMapper.toDto(sensor));
    }

}
