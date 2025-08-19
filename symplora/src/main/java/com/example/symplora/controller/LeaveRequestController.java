// src/main/java/com/sympora/controller/LeaveRequestController.java
package com.example.symplora.controller;

import com.example.symplora.dto.LeaveRequestDto;
import com.example.symplora.entity.LeaveRequest;
import com.example.symplora.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveRequestController {

    private final LeaveRequestService leaveService;
    public LeaveRequestController(LeaveRequestService service) { this.leaveService = service; }

    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDto dto) {
        LeaveRequest req = leaveService.applyLeave(dto.employeeId, dto.startDate, dto.endDate, dto.reason);
        return ResponseEntity.ok(req);
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<?> approveLeave(@PathVariable Long requestId) {
        LeaveRequest req = leaveService.approveLeave(requestId);
        return ResponseEntity.ok(req);
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectLeave(@PathVariable Long requestId, @RequestBody String reason) {
        LeaveRequest req = leaveService.rejectLeave(requestId, reason);
        return ResponseEntity.ok(req);
    }

    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<?> getLeaveBalance(@PathVariable Long employeeId) {
        Integer balance = leaveService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(balance);
    }
}