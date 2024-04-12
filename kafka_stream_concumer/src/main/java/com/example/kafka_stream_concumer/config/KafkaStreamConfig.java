package com.example.kafka_stream_concumer.config;

import com.example.kafka_stream_concumer.domain.AggregateTelemetryDataSerde;
import com.example.kafka_stream_concumer.domain.AggregatedTelemetryData;
import com.example.kafka_stream_concumer.domain.TelemetryData;
import com.example.kafka_stream_concumer.domain.TelemetryDataSerde;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public Serde<TelemetryData> telemetryDataSerde() {
        return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(TelemetryData.class));
    }

    @Bean
    public KStream<String, String> KTable(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder
                .stream("hardcoded-words-out", Consumed.with(Serdes.String(), Serdes.String()));

        KGroupedStream<String, String> kGroupedStream = stream.groupBy((key, value) -> value);

        KTable<String, Long> kTable = kGroupedStream.count();

        kTable.toStream().print(Printed.<String,Long>toSysOut().withLabel("KTable - telemetry-data"));

        return stream;
    }

    @Bean
    public KStream<String, AggregatedTelemetryData> calculatedMaxSpeedAndTraveledDistance(StreamsBuilder kStreamBuilder) {
        KStream<String, TelemetryData> stream = kStreamBuilder
                .stream("space-probe-telemetry-data", Consumed.with(Serdes.String(), new TelemetryDataSerde()));
        return stream
                .groupBy((k,v) -> v.getProbeId(), Grouped.with(Serdes.String(), new TelemetryDataSerde()))
                .aggregate(
                        AggregatedTelemetryData::new,
                        (aggKey, newValue, aggValue) -> updateTotals(aggKey, newValue, aggValue),
                        Materialized.with(Serdes.String(), new AggregateTelemetryDataSerde()))
                .toStream();
    }

    public AggregatedTelemetryData updateTotals(
            String probeId,
            TelemetryData lastTelemetryReading,
            AggregatedTelemetryData currentAggregatedValue) {
        double totalDistanceTraveled =
                lastTelemetryReading.getTraveledDistanceFeet() + currentAggregatedValue.getTraveledDistanceFeet();
        double maxSpeed = Math.max(lastTelemetryReading.getCurrentSpeedMph(), currentAggregatedValue.getMaxSpeedMph());
        AggregatedTelemetryData aggregatedTelemetryData = new AggregatedTelemetryData(
                totalDistanceTraveled,
                maxSpeed
        );
        log.info("Calculated new aggregated telemetry data for probe {}. New max speed: {} and traveled distance {}",
                probeId, aggregatedTelemetryData.getMaxSpeedMph(), aggregatedTelemetryData.getTraveledDistanceFeet());
        return aggregatedTelemetryData;
    }

}