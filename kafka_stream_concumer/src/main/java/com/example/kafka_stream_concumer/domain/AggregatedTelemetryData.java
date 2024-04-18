package com.example.kafka_stream_concumer.domain;

import lombok.Data;

@Data
public class AggregatedTelemetryData{
    private Double maxSpeedMph;
    private Double traveledDistanceFeet;
    private Integer counter;

    public AggregatedTelemetryData() {
        this.maxSpeedMph = 0.0;
        this.traveledDistanceFeet = 0.0;
        this.counter = 0;
    }

    public AggregatedTelemetryData(Double maxSpeedMph, Double traveledDistanceFeet, Integer counter) {
        this.maxSpeedMph = maxSpeedMph;
        this.traveledDistanceFeet = traveledDistanceFeet;
        this.counter = counter;
    }
}
