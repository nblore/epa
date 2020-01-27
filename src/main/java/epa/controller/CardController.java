package epa.controller;

import epa.entity.Card;
import epa.entity.Employee;
import epa.service.CardService;
import epa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;



@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private EmployeeService employeeService;



    @RequestMapping(value = "/card/login", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginCard(@RequestParam String id) {
        Optional<Card> card = cardService.findById(id);

        Optional<Employee> employee = employeeService.findById(card.get().getEmployee_id());
        return new ResponseEntity<>("Welcome " + employee.get().getName(), HttpStatus.OK);

    }
}
