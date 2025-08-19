package com.example.symplora.entity;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(Integer totalLeave) {
        this.totalLeave = totalLeave;
    }

    public Integer getUsedLeave() {
        return usedLeave;
    }

    public void setUsedLeave(Integer usedLeave) {
        this.usedLeave = usedLeave;
    }

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String department;

    @Column(nullable=false)
    private LocalDate joiningDate;

    private Integer totalLeave = 24;
    private Integer usedLeave = 0;

    // Getters and Setters
}
