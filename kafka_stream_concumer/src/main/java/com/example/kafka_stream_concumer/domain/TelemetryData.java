package com.example.kafka_stream_concumer.domain;

public record TelemetryData(
        String probeId,
        String timestamp,
        Double currentSpeedMph,
        Double traveledDistanceFeet,
        SpaceAgency spaceAgency
){ }

