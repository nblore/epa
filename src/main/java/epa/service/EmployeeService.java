package epa.service;

import epa.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();
    Employee save(Employee employee);
    Optional<Employee> findById(String employee_id);
}

