package com.example.sensors.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SensorsDtoExtra implements Serializable {

    private List<SensorsDto> sensorsDtos;
    private int totalPages;

    public SensorsDtoExtra(List<SensorsDto> sensorsDtos, int totalPages) {
        this.sensorsDtos = sensorsDtos;
        this.totalPages = totalPages;
    }

    public List<SensorsDto> getSensorsDtos() {
        return sensorsDtos;
    }

    public void setSensorsDtos(List<SensorsDto> sensorsDtos) {
        this.sensorsDtos = sensorsDtos;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
