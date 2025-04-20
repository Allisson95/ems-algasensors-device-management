package dev.allisson.algasensors.device.management.api.controller;

import dev.allisson.algasensors.device.management.api.mapper.SensorMapper;
import dev.allisson.algasensors.device.management.api.model.SensorInput;
import dev.allisson.algasensors.device.management.api.model.SensorOutput;
import dev.allisson.algasensors.device.management.domain.model.Sensor;
import dev.allisson.algasensors.device.management.domain.model.SensorId;
import dev.allisson.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;

    @GetMapping("{sensorId}")
    public ResponseEntity<SensorOutput> getOne(@PathVariable final TSID sensorId) {
        log.info("Received a new request to get a sensor by id: {}", sensorId);
        final Sensor sensor = this.sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(this.sensorMapper.toDto(sensor));
    }

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
