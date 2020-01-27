package epa.controller;

import epa.entity.Card;
import epa.entity.Employee;
import epa.entity.EmployeeCardRegistration;
import epa.entity.LoginDetails;
import epa.service.CardService;
import epa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
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

    @RequestMapping(value = "/employee/login", method = RequestMethod.POST)
    public ResponseEntity<String> loginCard(@RequestBody LoginDetails loginDetails) {
        Optional<Card> card = cardService.findById(loginDetails.getCardId());

        if (card.isPresent()) {
            Employee card_empl = employeeService.findById(card.get().getEmployee_id()).get();
            if (loginDetails.getPassword() == card_empl.getPin()) {
                if(lastActivityPlusTimeoutCalculator(card_empl).before( new Timestamp(System.currentTimeMillis()))){
                    card_empl.setSession_time(new Timestamp(System.currentTimeMillis()));
                    employeeService.save(card_empl);
                    return new ResponseEntity<String>("Welcome " + card_empl.getName(), HttpStatus.OK);

                }else{
                    card_empl.setSession_time(Timestamp.valueOf("1971-01-01 00:00:01"));
                    employeeService.save(card_empl);
                    return new ResponseEntity<String>("Goodbye " + card_empl.getName(), HttpStatus.OK);

                }

            } else {
                return new ResponseEntity<String>("Incorrect Pin ", HttpStatus.FORBIDDEN);
            }

        } else {
            return new ResponseEntity<>("Cards not found ", HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/employee/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestParam String id) {
        Optional<Employee> employeeToDelete = employeeService.findById(id);
        employeeService.delete(employeeToDelete.get());
        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBalance(@RequestBody EmployeeCardRegistration employeeReg) {

        Optional<Card> card = cardService.findById(employeeReg.getCardId());

        if (card.isPresent()) {
            Employee card_empl = employeeService.findById(card.get().getEmployee_id()).get();
            return new ResponseEntity<String>("Card is already registered. Use a different card ID", HttpStatus.OK);

        } else {
            Employee empl = new Employee();
            empl.setId(employeeReg.getId());
            empl.setName(employeeReg.getName());
            empl.setEmail(employeeReg.getEmail());
            empl.setMobile(employeeReg.getMobile());
            empl.setPin(employeeReg.getPin());
            empl.setSession_time(new Timestamp(System.currentTimeMillis()));
            employeeService.save(empl);
            return new ResponseEntity<String>("New User Registered " + empl.getId(), HttpStatus.OK);

        }
    }

    private Timestamp lastActivityPlusTimeoutCalculator(Employee empl){
        int timeoutInSeconds = 15;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(empl.getSession_time().getTime());
        cal.add(Calendar.SECOND, timeoutInSeconds);
        return new Timestamp(cal.getTime().getTime());
    }
}
