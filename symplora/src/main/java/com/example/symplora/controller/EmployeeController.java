// src/main/java/com/sympora/controller/EmployeeController.java
package com.example.symplora.controller;

import com.example.symplora.dto.EmployeeDto;
import com.example.symplora.entity.Employee;
import com.example.symplora.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService service) { this.employeeService = service; }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto dto) {
        Employee emp = new Employee();
        emp.setName(dto.name);
        emp.setEmail(dto.email);
        emp.setDepartment(dto.department);
        emp.setJoiningDate(dto.joiningDate);
        Employee saved = employeeService.addEmployee(emp);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> leaveBalance(@PathVariable Long id) {
        Integer bal = employeeService.getEmployee(id).getTotalLeave() -
                employeeService.getEmployee(id).getUsedLeave();
        return ResponseEntity.ok(bal);
    }
}