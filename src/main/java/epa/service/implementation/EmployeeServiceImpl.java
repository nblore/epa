package epa.service.implementation;

import epa.entity.Employee;
import epa.repository.EmployeeRepository;
import epa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() { return employeeRepository.findAll(); }

    @Override
    public Employee save(Employee employee) { return employeeRepository.save(employee); }

    @Override
    public Optional<Employee> findById(String employee_id) { return employeeRepository.findById(employee_id); }
}

