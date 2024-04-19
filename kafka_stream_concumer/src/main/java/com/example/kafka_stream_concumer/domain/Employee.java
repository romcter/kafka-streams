package com.example.kafka_stream_concumer.domain;

import lombok.Data;

@Data
public class Employee {
    public Integer id;
    public String name;
    public EmployeeDepartment department;
    public Integer salary;
}
