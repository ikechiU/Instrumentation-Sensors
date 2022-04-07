package com.example.sensors.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SensorsDtoExtra implements Serializable {

    private List<SensorsDto> sensorsDtos;
    private int pageListCount;
    private int currentPage;
    private String previousPage;
    private String nextPage;
    private long totalListCount;
    private int totalPages;

    public SensorsDtoExtra(List<SensorsDto> sensorsDtos, int pageListCount, int currentPage, String previousPage, String nextPage, long totalListCount, int totalPages) {
        this.sensorsDtos = sensorsDtos;
        this.pageListCount = pageListCount;
        this.currentPage = currentPage;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.totalListCount = totalListCount;
        this.totalPages = totalPages;
    }

    public List<SensorsDto> getSensorsDtos() {
        return sensorsDtos;
    }

    public void setSensorsDtos(List<SensorsDto> sensorsDtos) {
        this.sensorsDtos = sensorsDtos;
    }

    public int getPageListCount() {
        return pageListCount;
    }

    public void setPageListCount(int pageCount) {
        this.pageListCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public long getTotalListCount() {
        return totalListCount;
    }

    public void setTotalListCount(long totalCount) {
        this.totalListCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
