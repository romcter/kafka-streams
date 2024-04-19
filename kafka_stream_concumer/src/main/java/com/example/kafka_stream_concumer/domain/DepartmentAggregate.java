package com.example.kafka_stream_concumer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentAggregate {
    public Integer employeeCount;
    public Integer totalSalary;
    public Integer avgSalary;

    public DepartmentAggregate() {
        this.employeeCount = 0;
        this.totalSalary = 0;
        this.avgSalary = 0;
    }

    public DepartmentAggregate(int employeeCount, int totalSalary, int avgSalary) {
        this.employeeCount = employeeCount;
        this.totalSalary = totalSalary;
        this.avgSalary = avgSalary;
    }
}
