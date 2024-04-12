package com.example.kafka_stream_concumer.domain;

public class AggregatedTelemetryData{
    private Double maxSpeedMph;
    private Double traveledDistanceFeet;

    public AggregatedTelemetryData() {
        this.maxSpeedMph = 0.0;
        this.traveledDistanceFeet = 0.0;
    }

    public AggregatedTelemetryData(Double maxSpeedMph, Double traveledDistanceFeet) {
        this.maxSpeedMph = maxSpeedMph;
        this.traveledDistanceFeet = traveledDistanceFeet;
    }

    public Double getMaxSpeedMph() {
        return maxSpeedMph;
    }

    public void setMaxSpeedMph(Double maxSpeedMph) {
        this.maxSpeedMph = maxSpeedMph;
    }

    public Double getTraveledDistanceFeet() {
        return traveledDistanceFeet;
    }

    public void setTraveledDistanceFeet(Double traveledDistanceFeet) {
        this.traveledDistanceFeet = traveledDistanceFeet;
    }
}
