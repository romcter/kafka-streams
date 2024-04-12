package com.example.kafka_stream_concumer.config;

import com.example.kafka_stream_concumer.domain.TelemetryData;
import com.example.kafka_stream_concumer.domain.TelemetryDataSerde;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
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

//    @Bean
//    public KTable<String, TelemetryData> kTable(StreamsBuilder kStreamBuilder) {
//
//        KTable<String, TelemetryData> userStream = kStreamBuilder.table("space-probe-telemetry-data");
//        userStream.toStream().print(Printed.<String,TelemetryData>toSysOut().withLabel("KTable - telemetry-data"));
//
//        return userStream;
//    }

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
    public KStream<String, TelemetryData> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, TelemetryData> userStream = kStreamBuilder
                .stream("space-probe-telemetry-data", Consumed.with(Serdes.String(), new TelemetryDataSerde()));
//        KStream<String, TelemetryData> userStream = stream
//              .mapValues(this::getTelemetryDataFromString);
//                .flatMapValues(v -> Arrays.asList());

        userStream.print(Printed.<String,TelemetryData>toSysOut().withLabel("KStream - telemetry-data"));
//
        var t = userStream.groupBy((k,v) -> v.probeId())
                .reduce((k,v) -> v );

//        t.toStream().print(Printed.<String,Long>toSysOut().withLabel("KTable - telemetry-data"));
//
//        userStream.to("telemetry-data", Produced.with(Serdes.String(), userSerde()));

//        KTable<String, TelemetryData> userStream = kStreamBuilder.table("space-probe-telemetry-data");
        t.toStream().print(Printed.<String,TelemetryData>toSysOut().withLabel("KTable - telemetry-data"));

        return userStream;
    }

    String printValue(String userString){
        log.info("Successfully read "+userString);
        return userString;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    TelemetryData getTelemetryDataFromString(String telemetryDataString) {
        TelemetryData user = null;
        try {
            user = objectMapper().readValue(telemetryDataString, TelemetryData.class);
            log.info("Successfully read "+ user);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return user;
    }


}