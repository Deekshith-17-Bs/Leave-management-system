package com.example.symplora.service;



import com.example.symplora.entity.Employee;
import com.example.symplora.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;

    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        Optional<Employee> existing = employeeRepo.findByEmail(employee.getEmail());
        if (existing.isPresent())
            throw new RuntimeException("Employee with this email already exists");
        return employeeRepo.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Employee not found"));
    }
}
