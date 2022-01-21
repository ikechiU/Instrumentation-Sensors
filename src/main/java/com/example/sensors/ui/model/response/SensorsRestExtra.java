package com.example.sensors.ui.model.response;

import java.util.List;

public class SensorsRestExtra {
    private List<SensorsRest> sensorsRests;
    private int totalPages;

    public SensorsRestExtra(List<SensorsRest> sensorsRests, int totalPages) {
        this.sensorsRests = sensorsRests;
        this.totalPages = totalPages;
    }

    public List<SensorsRest> getSensorsRests() {
        return sensorsRests;
    }

    public void setSensorsRests(List<SensorsRest> sensorsRests) {
        this.sensorsRests = sensorsRests;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
