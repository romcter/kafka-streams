package com.example.kafka_stream_concumer;

import com.example.kafka_stream_concumer.domain.DepartmentAggregate;
import com.example.kafka_stream_concumer.domain.DepartmentAggregateSerde;
import com.example.kafka_stream_concumer.domain.Employee;
import com.example.kafka_stream_concumer.domain.EmployeeDataSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Properties;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        var stream = streamsBuilder
                .table("calculate-department-salary", Consumed.with(Serdes.String(), new EmployeeDataSerde()))
//                .groupBy((k,v) -> KeyValue.pair(v.getDepartment(), v), Grouped.with(Serdes.String(), new EmployeeDataSerde()))
                .groupBy((k, v) -> KeyValue.pair(v.getDepartment().toString(), v), Grouped.with(Serdes.String(), new EmployeeDataSerde()))
                .aggregate(
                        DepartmentAggregate::new,
                        (k, v, aggV) -> addEmployee(v, aggV),
                        (k, v, aggV) -> deleteEmployee(v, aggV),
                        Materialized.with(Serdes.String(), new DepartmentAggregateSerde())
//                        ,Materialized.<String, DepartmentAggregate, KeyValueStore<Byte, byte[]>>as(
//                                "").withValueSerde(new DepartmentAggregateSerde())
                ).toStream();
        stream.print(Printed.<String, DepartmentAggregate>toSysOut().withLabel("Employee department"));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Stopping stream");
            streams.close();
        }));
    }

    private static DepartmentAggregate addEmployee(Employee v, DepartmentAggregate aggV) {
        log.info("ADD: V is {}, aggV is {}", v, aggV);
        return DepartmentAggregate.builder()
                .employeeCount(aggV.employeeCount + 1)
                .totalSalary(aggV.totalSalary + v.salary)
                .avgSalary((aggV.totalSalary + v.salary) / (aggV.employeeCount + 1))
//                .employeeCount(0)
//                .totalSalary(0)
//                .avgSalary(0)
                .build();
    }

    private static DepartmentAggregate deleteEmployee(Employee v, DepartmentAggregate aggV) {
        log.info("DELETE: V is {}, aggV is {}", v, aggV);
        return DepartmentAggregate.builder()
//                .employeeCount(0)
//                .totalSalary(0)
//                .avgSalary(0)
                .employeeCount(aggV.employeeCount - 1)
                .totalSalary(aggV.totalSalary - v.salary)
                .avgSalary((aggV.totalSalary - v.salary) / (aggV.employeeCount <= 1 ? 1 : aggV.employeeCount -1))
                .build();
    }
}
