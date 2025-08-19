package com.example.symplora.dto;

import java.time.LocalDate;
public class LeaveRequestDto {
    public Long employeeId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String reason;
}
