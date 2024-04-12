package com.example.kafka_stream_concumer.domain;

public record AggregatedTelemetryData(
        Double maxSpeedMph,
        Double traveledDistanceFeet
) { }
