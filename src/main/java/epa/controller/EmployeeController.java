package epa.controller;

import epa.entity.Card;
import epa.entity.Employee;
import epa.entity.EmployeeCardRegistration;
import epa.service.CardService;
import epa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private CardService cardService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> greetingJson(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();

        return new ResponseEntity<String>("Logged In - "+ json , HttpStatus.OK);
    }



    @RequestMapping(value = "/employee/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestParam String id) {
        Optional<Employee> employeeToDelete = employeeService.findById(id);
        employeeService.delete(employeeToDelete.get());
        return new ResponseEntity<String>("User: " + employeeToDelete.get().getName() + ", was deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBalance(@RequestBody EmployeeCardRegistration employeeReg) {

        Optional<Card> card = cardService.findById(employeeReg.getCardId());
        Employee empl = new Employee();
        Card card1 = new Card();

        if (card.isPresent()) {
            return new ResponseEntity<String>("Card: "+ card.get().getId() + " already registered", HttpStatus.OK);

        } else {
            empl.setId(employeeReg.getId());
            empl.setName(employeeReg.getName());
            empl.setEmail(employeeReg.getEmail());
            empl.setMobile(employeeReg.getMobile());
            empl.setPin(employeeReg.getPin());
            card1.setBalance(employeeReg.getBalance());

            employeeService.save(empl);
            return new ResponseEntity<String>("New User Registered: " + empl.getName(), HttpStatus.OK);

        }
    }
}
