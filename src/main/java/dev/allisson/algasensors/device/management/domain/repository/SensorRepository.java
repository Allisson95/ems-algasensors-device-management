package dev.allisson.algasensors.device.management.domain.repository;

import dev.allisson.algasensors.device.management.domain.model.Sensor;
import dev.allisson.algasensors.device.management.domain.model.SensorId;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SensorRepository
        extends BaseJpaRepository<Sensor, SensorId>,
        PagingAndSortingRepository<Sensor, SensorId> {
}