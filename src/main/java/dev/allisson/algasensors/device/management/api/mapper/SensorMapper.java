package dev.allisson.algasensors.device.management.api.mapper;

import dev.allisson.algasensors.device.management.api.model.SensorInput;
import dev.allisson.algasensors.device.management.api.model.SensorOutput;
import dev.allisson.algasensors.device.management.domain.model.Sensor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorMapper {
    Sensor toEntity(SensorInput sensorInput);

    @Mapping(source = "id.value", target = "id")
    SensorOutput toDto(Sensor sensor);
}