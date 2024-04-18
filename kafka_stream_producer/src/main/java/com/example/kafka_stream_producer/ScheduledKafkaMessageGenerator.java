package com.example.kafka_stream_producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledKafkaMessageGenerator {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Gson gson = new Gson();;

    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
    public void emitSampleTelemetryData() {
        int nextInt = new Random().nextInt(10);
        TelemetryData telemetryData = new TelemetryData(
                Integer.toString(nextInt),
                LocalDateTime.now().toString(),
                new Random().nextDouble(0.0, 1000.0),
                new Random().nextDouble(1.0, 10000.0),
                nextInt < 5 ? SpaceAgency.NASA : SpaceAgency.ESA,
                new Random().nextDouble(0.0, 10.0)
        );
        log.info("Telemetry data send {}", telemetryData);

        String json = gson.toJson(telemetryData);

        var random = String.valueOf(new Random().nextInt(10));

        kafkaTemplate.send("space-probe-telemetry-data", random, json);
        kafkaTemplate.send("count-probe-telemetry-data", random, json);
    }

    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
    public void sendHardcodedWords() {
        List<String> listOfWords = Arrays.asList("Kafka", "test", "Telem", "marafon", "chinazes", "lawe", "teror", "error", "past", "simple");
//        Message<?> kafkaMessage = MessageBuilder
//                .withPayload(listOfWords.get(new Random().nextInt(listOfWords.size())))
//                .build();
        var word = listOfWords.get(new Random().nextInt(listOfWords.size()));
        log.info("Telemetry data send {}", word);
//        kafkaTemplate.send("hardcoded-words-out", word);
    }
}
