package com.example.symplora.service;



import com.example.symplora.entity.Employee;
import com.example.symplora.entity.LeaveRequest;
import com.example.symplora.repo.EmployeeRepository;
import com.example.symplora.repo.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepo;
    private final EmployeeRepository employeeRepo;

    public LeaveRequestService(LeaveRequestRepository repo, EmployeeRepository empRepo) {
        this.leaveRequestRepo = repo;
        this.employeeRepo = empRepo;
    }

    public LeaveRequest applyLeave(Long employeeId, LocalDate start, LocalDate end, String reason) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        if (start.isBefore(emp.getJoiningDate()))
            throw new RuntimeException("Cannot apply for leave before joining date");
        if (end.isBefore(start))
            throw new RuntimeException("End date cannot be before start date");
        int days = (int) (end.toEpochDay() - start.toEpochDay()) + 1;

        int remaining = emp.getTotalLeave() - emp.getUsedLeave();
        if (days > remaining)
            throw new RuntimeException("Insufficient leave balance");

        // Overlapping leave check
        List<LeaveRequest> overlap = leaveRequestRepo
                .findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(employeeId, end, start);
        boolean hasOverlap = overlap.stream().anyMatch(r -> r.getStatus() == LeaveRequest.Status.APPROVED ||
                r.getStatus() == LeaveRequest.Status.PENDING);

        if (hasOverlap)
            throw new RuntimeException("Leave request overlaps with existing approved/pending request");

        LeaveRequest lr = new LeaveRequest();
        lr.setEmployee(emp);
        lr.setStartDate(start);
        lr.setEndDate(end);
        lr.setReason(reason);
        lr.setStatus(LeaveRequest.Status.PENDING);

        return leaveRequestRepo.save(lr);
    }

    public LeaveRequest approveLeave(Long requestId) {
        LeaveRequest req = leaveRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        if (req.getStatus() != LeaveRequest.Status.PENDING)
            throw new RuntimeException("Leave request already resolved");

        int days = (int) (req.getEndDate().toEpochDay() - req.getStartDate().toEpochDay()) + 1;
        Employee emp = req.getEmployee();

        int remaining = emp.getTotalLeave() - emp.getUsedLeave();
        if (days > remaining)
            throw new RuntimeException("Insufficient leave balance at approval stage");

        req.setStatus(LeaveRequest.Status.APPROVED);
        leaveRequestRepo.save(req);

        emp.setUsedLeave(emp.getUsedLeave() + days);
        employeeRepo.save(emp);
        return req;
    }

    public LeaveRequest rejectLeave(Long requestId, String reason) {
        LeaveRequest req = leaveRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        if (req.getStatus() != LeaveRequest.Status.PENDING)
            throw new RuntimeException("Leave request already resolved");
        req.setStatus(LeaveRequest.Status.REJECTED);
        req.setReason(reason);
        return leaveRequestRepo.save(req);
    }

    public Integer getLeaveBalance(Long employeeId) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return emp.getTotalLeave() - emp.getUsedLeave();
    }
}