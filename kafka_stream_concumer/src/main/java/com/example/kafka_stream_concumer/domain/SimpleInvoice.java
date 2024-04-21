
package com.example.kafka_stream_concumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleInvoice {
    private String invoiceNumber;
    private Long createdTime;
    private String storeID;
    private Double totalAmount;
}