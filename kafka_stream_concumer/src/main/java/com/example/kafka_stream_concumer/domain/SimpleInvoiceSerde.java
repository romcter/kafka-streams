package com.example.kafka_stream_concumer.domain;

import org.springframework.kafka.support.serializer.JsonSerde;

public class SimpleInvoiceSerde extends JsonSerde<SimpleInvoice> {
}