package epa.repository;

import epa.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*** 
Allows database interaction using spring data
***/
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

    List<Employee> findAll();
    Employee save(Employee employee);
}
