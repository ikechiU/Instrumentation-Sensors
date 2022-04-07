package com.example.sensors.service;

import com.example.sensors.shared.dto.SensorsDto;
import com.example.sensors.shared.dto.SensorsDtoExtra;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SensorsService {

    SensorsDto addSensor(SensorsDto sensorsDto, MultipartFile file);

    SensorsDto getSensor(String queryParam);

    SensorsDto updateSensor(String queryParam, SensorsDto sensorsDto, MultipartFile file) throws IOException;

    List<SensorsDto> getSensors(int page, int limit);

    SensorsDtoExtra getSensorsExtra(int page, int limit);

    String deleteSensor(String queryParam) throws IOException;

}
