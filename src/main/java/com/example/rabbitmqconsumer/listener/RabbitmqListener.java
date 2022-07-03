package com.example.rabbitmqconsumer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rabbitmqconsumer.config.RabbitmqConfig;
import com.example.rabbitmqconsumer.entity.Employee;
import com.example.rabbitmqconsumer.service.EmployeeService;

@Component
public class RabbitmqListener {
    @Autowired
    EmployeeService service;
    @RabbitListener(queues = RabbitmqConfig.QueueName)
    public String updateEmployeeListener(String employee) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Employee employeeObject = mapper.readValue(employee, Employee.class);
            System.out.println("[.] Got request: " + employeeObject);
            Employee updatedEmployee = service.updateEmployee(employeeObject);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedEmployee);
            return json;
     
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
