package com.example.rabbitmqconsumer.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rabbitmqconsumer.config.RabbitmqConfig;
import com.example.rabbitmqconsumer.entity.Employee;
import com.example.rabbitmqconsumer.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public Employee save(Employee employee){
        System.out.println("emp service: " + employee);
        return repo.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employees){

        return repo.saveAll(employees);
    }

    public List<Employee> getAll(){
        return repo.findAllEmployees();
    }

    public Employee getEmp(String id){
        Employee employee = repo.findById(id).get();
        if(employee == null) return null;
        if(employee.getIsDeleted()) return null;
        return employee;
    }

    public Employee updateEmployee(Employee employee){
        Employee prev_employee = repo.findById(employee.getId()).get();

        if(prev_employee == null){
            return null;
        }

        if(prev_employee.getIsDeleted() == true) {
            return null;
        }

        prev_employee.setName(employee.getName());
        prev_employee.setPod(employee.getPod());
        prev_employee.setContact(employee.getContact());
        prev_employee.setAge(employee.getAge());

        return repo.save(prev_employee);

    }

    public String deleteEmp(String id){
        Employee prev_employee = repo.findById(id).get();
        if(prev_employee != null){
            if(prev_employee.getIsDeleted() == true){
                return "No employee with id: " + id;
            }
            prev_employee.setIsDeleted(true);
            repo.save(prev_employee);
            return "Employee removed with id: " + id;
        }
        
        return "No employee with id: " + id;
    }

    // @RabbitListener(queues = RabbitmqConfig.QueueName)
    // public Employee updateEmployeeListener(Employee employee) throws Exception {
    //     try {
    //         System.out.println("[.] Got request: " + employee);
    //         Employee updatedEmployee = updateEmployee(employee);
    //         return updatedEmployee;
     
    //     } catch (Exception e) {
    //         e.getStackTrace();
    //         return null;
    //     }
    // }
}
