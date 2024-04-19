package com.example.kafka_stream_producer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    public Integer id;
    public String name;
    public EmployeeDepartment department;
    public Integer salary;
}
