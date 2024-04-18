package com.example.kafka_stream_concumer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class TelemetryDataDeserializer implements Deserializer<TelemetryData> {
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public Serializer<TelemetryData> serializer() {
//        return null;
//    }
//
//    @Override
//    public Deserializer<TelemetryData> deserializer() {
//        try {
//            if (data == null){
//                System.out.println("Null received at deserializing");
//                return null;
//            }
//            System.out.println("Deserializing...");
//            return objectMapper.readValue(new String(data, "UTF-8"), MessageDto.class);
//        } catch (Exception e) {
//            throw new SerializationException("Error when deserializing byte[] to MessageDto");
//        }
//    }
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public TelemetryData deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), TelemetryData.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
