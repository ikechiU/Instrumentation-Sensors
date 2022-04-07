package com.example.sensors.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sensors_tb")
public class SensorsEntity implements Serializable {

    private static final long serialVersionUID = -23714379761985116L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 15)
    private String sensorId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column(nullable = false)
    private String source;

    private String moreInfo;

    @Column(length = 150)
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
