package dev.allisson.algasensors.device.management.api.controller;

import dev.allisson.algasensors.device.management.api.client.SensorMonitoringClient;
import dev.allisson.algasensors.device.management.api.mapper.SensorMapper;
import dev.allisson.algasensors.device.management.api.model.SensorDetailOutput;
import dev.allisson.algasensors.device.management.api.model.SensorInput;
import dev.allisson.algasensors.device.management.api.model.SensorMonitoringOutput;
import dev.allisson.algasensors.device.management.api.model.SensorOutput;
import dev.allisson.algasensors.device.management.domain.model.Sensor;
import dev.allisson.algasensors.device.management.domain.model.SensorId;
import dev.allisson.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final SensorMonitoringClient sensorMonitoringClient;

    @GetMapping
    public ResponseEntity<Page<SensorOutput>> search(@PageableDefault final Pageable pageable) {
        log.info("Received a new request to search for sensors");
        final Page<Sensor> sensors = this.sensorRepository.findAll(pageable);
        return ResponseEntity.ok(sensors.map(this.sensorMapper::toDto));
    }

    @GetMapping("{sensorId}")
    public ResponseEntity<SensorOutput> getOne(@PathVariable final TSID sensorId) {
        log.info("Received a new request to get a sensor by id: {}", sensorId);
        final Sensor sensor = this.sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(this.sensorMapper.toDto(sensor));
    }

    @GetMapping("{sensorId}/detail")
    public ResponseEntity<SensorDetailOutput> getOneWithDetail(@PathVariable final TSID sensorId) {
        log.info("Received a new request to get a sensor with detail by id: {}", sensorId);
        final SensorId id = new SensorId(sensorId);
        final Sensor sensor = this.sensorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final SensorMonitoringOutput monitoring = this.sensorMonitoringClient.getMonitoringDetail(id);
        return ResponseEntity.ok(new SensorDetailOutput(this.sensorMapper.toDto(sensor), monitoring));
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{sensorId}")
    public void delete(@PathVariable TSID sensorId) {
        log.info("Received a new request to delete a sensor by id: {}", sensorId);
        final SensorId id = new SensorId(sensorId);
        final Sensor sensor = this.sensorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.sensorRepository.delete(sensor);
        this.sensorMonitoringClient.disableMonitoring(id);
    }

    @PutMapping("{sensorId}")
    public ResponseEntity<SensorOutput> update(@PathVariable TSID sensorId, @RequestBody final SensorInput input) {
        log.info("Received a new request to update a sensor by id: {} with new data: {}", sensorId, input);
        final Sensor sensor = this.sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.sensorMapper.partialUpdate(input, sensor);
        this.sensorRepository.update(sensor);
        return ResponseEntity.ok(this.sensorMapper.toDto(sensor));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{sensorId}/enable")
    public void enable(@PathVariable TSID sensorId) {
        log.info("Received a new request to enable a sensor by id: {}", sensorId);
        final SensorId id = new SensorId(sensorId);
        final Sensor sensor = this.sensorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.enable();
        this.sensorRepository.update(sensor);
        this.sensorMonitoringClient.enableMonitoring(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{sensorId}/enable")
    public void disable(@PathVariable TSID sensorId) {
        log.info("Received a new request to disable a sensor by id: {}", sensorId);
        final SensorId id = new SensorId(sensorId);
        final Sensor sensor = this.sensorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.disable();
        this.sensorRepository.update(sensor);
        this.sensorMonitoringClient.disableMonitoring(id);
    }

}
