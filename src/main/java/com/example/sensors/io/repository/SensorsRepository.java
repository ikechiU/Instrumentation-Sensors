package com.example.sensors.io.repository;

import com.example.sensors.io.entity.SensorsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends PagingAndSortingRepository<SensorsEntity, Long> {

    SensorsEntity findSensorsEntitiesBySensorId(String sensorId);
    SensorsEntity findSensorsEntitiesByTitle(String title);
    SensorsEntity findSensorsEntitiesByDescription(String description);
}
