package com.example.kafka_stream_producer;

import com.example.kafka_stream_producer.event.TelemetryData;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.MessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TelemetryDataStreamBridge {

    private final Logger logger = LoggerFactory.getLogger(TelemetryDataStreamBridge.class);

    private final StreamBridge streamBridge;

    @Autowired
    public TelemetryDataStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void send(TelemetryData telemetryData) {
        Message<String> kafkaMessage = MessageBuilder
                .withPayload("kafka, test, wal")
                // Make sure all messages for a given probe go to the same partition to ensure proper ordering
                .setHeader("kafka_messageKey", telemetryData.probeId())
                .build();
        logger.info("Publishing space probe telemetry data: Payload: '{}'", kafkaMessage.getPayload());
        streamBridge.send("telemetry-data-out-0", kafkaMessage);
    }
}


