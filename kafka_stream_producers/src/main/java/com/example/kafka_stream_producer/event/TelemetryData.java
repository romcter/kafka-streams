package com.example.kafka_stream_producer.event;

public record TelemetryData(
        String probeId,
        String timestamp,
        Double currentSpeedMph,
        Double traveledDistanceFeet,
        SpaceAgency spaceAgency
){ }

