package com.example.kafka_stream_concumer.domain;

public class TelemetryData {
    private String probeId;
    private String timestamp;
    private Double currentSpeedMph;
    private Double traveledDistanceFeet;
    private SpaceAgency spaceAgency;
    private Double superImportantValue;
    private Double totalSuperImportantValue;

    public TelemetryData() {
        this.probeId = "";
        this.timestamp = "";
        this.currentSpeedMph = 0.0;
        this.traveledDistanceFeet = 0.0;
        this.spaceAgency = SpaceAgency.NONE;
        this.superImportantValue = 0.0;
        this.totalSuperImportantValue = 0.0;
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

    public Double getTotalSuperImportantValue() {
        return totalSuperImportantValue;
    }

    public void setTotalSuperImportantValue(Double totalSuperImportantValue) {
        this.totalSuperImportantValue = totalSuperImportantValue;
    }
}

