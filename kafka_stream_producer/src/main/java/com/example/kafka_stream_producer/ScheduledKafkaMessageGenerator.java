package com.example.kafka_stream_producer;

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
    private final KafkaTemplate<String, TelemetryData> kafkaTemplate;

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
        Message<?> kafkaMessage = MessageBuilder
                .withPayload(telemetryData)
                .setHeader(KafkaHeaders.TOPIC, String.valueOf(new Random().nextInt(10)))

                .build();
        log.info("Telemetry data send {}", telemetryData);
        kafkaTemplate.send("space-probe-telemetry-data", String.valueOf(new Random().nextInt(10)), telemetryData);
    }

//    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
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
