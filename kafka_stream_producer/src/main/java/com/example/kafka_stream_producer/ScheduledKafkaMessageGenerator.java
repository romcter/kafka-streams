package com.example.kafka_stream_producer;

import com.example.kafka_stream_producer.domain.Employee;
import com.example.kafka_stream_producer.domain.SpaceAgency;
import com.example.kafka_stream_producer.domain.TelemetryData;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.kafka_stream_producer.domain.EmployeeDepartment.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledKafkaMessageGenerator {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Gson gson = new Gson();
    private Integer counter = 0;

    //    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
    public void emitSampleTelemetryData() {
        int nextInt = new Random().nextInt(100, 110);
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

        var random = String.valueOf(new Random().nextInt(100, 110));
        kafkaTemplate.send("space-probe-telemetry-data", random, json);
        kafkaTemplate.send("count-probe-telemetry-data", random, json);
    }

    //    @Scheduled(initialDelay = 5000L, fixedRate = 1000L)
    public void sendHardcodedWords() {
        List<String> listOfWords = Arrays.asList("Kafka", "test", "Telem", "marafon", "chinazes", "lawe", "teror", "error", "past", "simple");
        var word = listOfWords.get(new Random().nextInt(listOfWords.size()));
        log.info("Telemetry data send {}", word);
        kafkaTemplate.send("hardcoded-words-out", word);
    }

    @Scheduled(initialDelay = 5000L, fixedRate = 10000L)
    public void sendEmployee() {
        List<Employee> list = new ArrayList<>(List.of(
                new Employee(1111, "Rom", EMPLOYEE_DEPARTMENT, 5000),
                new Employee(1112, "Tem", EMPLOYEE_DEPARTMENT, 3000),
                new Employee(1113, "Sem", ACCOUNTS_DEPARTMENT, 2000),
                new Employee(1114, "Max", EMPLOYEE_DEPARTMENT, 6000),
                new Employee(1115, "Vik", SUPPORT_DEPARTMENT, 2000),
                new Employee(1116, "Sik", BUSINESS_DEPARTMENT, 1000),
                new Employee(1114, "Max", ACCOUNTS_DEPARTMENT, 5000),
                new Employee(1115, "Vik", BUSINESS_DEPARTMENT, 4000)
        ));

        if(counter < list.size()) {
            var employee = list.get(counter);
            kafkaTemplate.send("calculate-department-salary", String.valueOf(employee.id), gson.toJson(employee));
            log.info("Send calculate-department-salary employee counter {}", counter);
            counter = counter + 1;
        }
    }
}
