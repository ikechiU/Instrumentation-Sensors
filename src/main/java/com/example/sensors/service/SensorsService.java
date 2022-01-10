package com.example.sensors.service;

import com.example.sensors.shared.dto.SensorsDto;

import java.util.List;

public interface SensorsService {
    SensorsDto addSensor(SensorsDto sensorsDto);

    SensorsDto getSensor(String info);

    SensorsDto updateSensor(String info, SensorsDto sensorsDto);

    List<SensorsDto> getSensors(int page, int limit);

    void deleteSensor(String sensorId);
}
