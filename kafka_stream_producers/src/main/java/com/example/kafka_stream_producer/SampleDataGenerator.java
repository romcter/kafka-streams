package com.example.kafka_stream_producer;

import com.example.kafka_stream_producer.event.SpaceAgency;
import com.example.kafka_stream_producer.event.TelemetryData;
//import de.codecentric.samples.kafkasamplesproducer.TelemetryDataStreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
//import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SampleDataGenerator {

//    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
//    public void emitSampleTelemetryData(){
//        var nextInt = Random.nextInt(10);
//        var spaceAgency = 0;
//        switch (nextInt){
//
//        }
//        var telemetryData = new TelemetryData(
//                nextInt.toString(),
//                Random.nextDouble(0.0, 1000.0),
//                Random.nextDouble(1.0, 10000.0),
//                spaceAgency = when {
//            nextInt < 5 -> {
//                SpaceAgency.NASA
//            }
//                else -> {
//                SpaceAgency.ESA
//            }
//        }
//        )
//        telemetryDataStreamBridge.send(telemetryData)
//    }

    private final TelemetryDataStreamBridge telemetryDataStreamBridge;

    public SampleDataGenerator(TelemetryDataStreamBridge telemetryDataStreamBridge) {
        this.telemetryDataStreamBridge = telemetryDataStreamBridge;
    }

    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
    public void emitSampleTelemetryData() {
        int nextInt = new Random().nextInt(10);
        TelemetryData telemetryData = new TelemetryData(
                String.valueOf(nextInt),
                LocalDateTime.now().toString(),
                new Random().nextDouble(0.0, 1000.0),
                new Random().nextDouble(1.0, 10000.0),
                nextInt < 5 ? SpaceAgency.NASA : SpaceAgency.ESA
        );
        telemetryDataStreamBridge.send(telemetryData);
    }
}
