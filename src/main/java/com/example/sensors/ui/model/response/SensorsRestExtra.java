package com.example.sensors.ui.model.response;

import java.util.List;

public class SensorsRestExtra {
    private List<SensorsRest> sensorsRests;
    private int pageListCount;
    private int currentPage;
    private String previousPage;
    private String nextPage;
    private long totalListCount;
    private int totalPages;

    public SensorsRestExtra(List<SensorsRest> sensorsRests, int pageListCount,  int currentPage, String previousPage, String nextPage, long totalListCount, int totalPages) {
        this.sensorsRests = sensorsRests;
        this.pageListCount = pageListCount;
        this.currentPage = currentPage;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.totalListCount = totalListCount;
        this.totalPages = totalPages;
    }

    public List<SensorsRest> getSensorsRests() {
        return sensorsRests;
    }

    public void setSensorsRests(List<SensorsRest> sensorsRests) {
        this.sensorsRests = sensorsRests;
    }

    public int getPageListCount() {
        return pageListCount;
    }

    public void setPageListCount(int pageListCount) {
        this.pageListCount = pageListCount;
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

    public void setTotalListCount(long totalListCount) {
        this.totalListCount = totalListCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
