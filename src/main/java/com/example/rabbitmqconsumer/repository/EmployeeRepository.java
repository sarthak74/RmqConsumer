package com.example.rabbitmqconsumer.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.rabbitmqconsumer.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("from Employee where isDeleted=false")
    List<Employee> findAllEmployees();
}
