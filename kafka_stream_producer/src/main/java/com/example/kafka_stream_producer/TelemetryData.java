package com.example.kafka_stream_producer;

import lombok.ToString;

@ToString
public class TelemetryData {
    private String probeId;
    private String timestamp;
    private Double currentSpeedMph;
    private Double traveledDistanceFeet;
    private SpaceAgency spaceAgency;
    private Double superImportantValue;

    public TelemetryData() {
        this.probeId = "";
        this.timestamp = "";
        this.currentSpeedMph = 0.0;
        this.traveledDistanceFeet = 0.0;
        this.spaceAgency = SpaceAgency.NONE;
        this.superImportantValue = 0.0;
    }

    public TelemetryData(String probeId, String timestamp, Double currentSpeedMph, Double traveledDistanceFeet, SpaceAgency spaceAgency, Double superImportantValue) {
        this.probeId = probeId;
        this.timestamp = timestamp;
        this.currentSpeedMph = currentSpeedMph;
        this.traveledDistanceFeet = traveledDistanceFeet;
        this.spaceAgency = spaceAgency;
        this.superImportantValue = superImportantValue;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCurrentSpeedMph() {
        return currentSpeedMph;
    }

    public void setCurrentSpeedMph(Double currentSpeedMph) {
        this.currentSpeedMph = currentSpeedMph;
    }

    public Double getTraveledDistanceFeet() {
        return traveledDistanceFeet;
    }

    public void setTraveledDistanceFeet(Double traveledDistanceFeet) {
        this.traveledDistanceFeet = traveledDistanceFeet;
    }

    public SpaceAgency getSpaceAgency() {
        return spaceAgency;
    }

    public void setSpaceAgency(SpaceAgency spaceAgency) {
        this.spaceAgency = spaceAgency;
    }

    public Double getSuperImportantValue() {
        return superImportantValue;
    }

    public void setSuperImportantValue(Double superImportantValue) {
        this.superImportantValue = superImportantValue;
    }

}

