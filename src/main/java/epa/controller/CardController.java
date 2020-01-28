package epa.controller;

import epa.entity.Card;
import epa.entity.CardLogin;
import epa.entity.CardTopup;
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


    @RequestMapping(value = "/card/login", method = RequestMethod.GET)
    public ResponseEntity<String> loginCard(@RequestParam String id) {
        Optional<Card> card = cardService.findById(id);

        Optional<Employee> employee = employeeService.findById(card.get().getEmployee_id());
        return new ResponseEntity<>("Welcome " + employee.get().getName(), HttpStatus.OK);

    }

    @RequestMapping(value = "/card/balance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBalance(@RequestBody CardLogin cardLogin) {
        String cardId = cardLogin.getCardId();
        Optional<Card> card = cardService.findById(cardId);
        if (card.isPresent()) {
            Employee empl = employeeService.findById(card.get().getEmployee_id()).get();
            if (cardLogin.getPassword() == empl.getPin()) {
                return new ResponseEntity<String>("Welcome " + empl.getName(), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Incorrect Pin ", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/card/topup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addBalance(@RequestBody CardTopup cardTopup) {
        String cardId = cardTopup.getCardId();
        Optional<Card> card = cardService.findById(cardId);
        if (card.isPresent()) {
            Employee empl = employeeService.findById(card.get().getEmployee_id()).get();
            if (cardTopup.getPassword() == empl.getPin()) {
                card.get().setBalance(card.get().getBalance()+ cardTopup.getBalance());
                cardService.save(card.get());
                return new ResponseEntity<String>("Balance Updated - New Balance: " + card.get().getBalance(), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Incorrect Pin ", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }
}
