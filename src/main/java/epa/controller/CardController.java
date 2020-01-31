package epa.controller;

import epa.entity.Card;
import epa.entity.LoginDetails;
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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;


@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/card/balance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBalance(@RequestBody LoginDetails loginDetails) {
        String cardId = loginDetails.getCardId();
        Optional<Card> card = cardService.findById(cardId);
        if (card.isPresent()) {
            Employee empl = employeeService.findById(card.get().getEmployee_id()).get();
            if (loginDetails.getPassword() == empl.getPin()) {

                if(lastActivityPlusTimeoutCalculator(empl).after( new Timestamp(System.currentTimeMillis()))){
                    empl.setSession_time(new Timestamp(System.currentTimeMillis()));
                    employeeService.save(empl);
                    return new ResponseEntity<String>("Welcome " + empl.getName() +" your balance is £" + card.get().getBalance(), HttpStatus.OK);

                } else {
                    return new ResponseEntity<String>("Session expired, please login again", HttpStatus.FORBIDDEN);
                }

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

                if(lastActivityPlusTimeoutCalculator(empl).after( new Timestamp(System.currentTimeMillis()))){
                    card.get().setBalance(card.get().getBalance()+ cardTopup.getBalance());
                    empl.setSession_time(new Timestamp(System.currentTimeMillis()));
                    cardService.save(card.get());
                    return new ResponseEntity<String>("Balance Updated - New Balance: £" + card.get().getBalance(), HttpStatus.OK);

                } else {
                    return new ResponseEntity<String>("Session expired, please login again", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<String>("Incorrect Pin ", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    private Timestamp lastActivityPlusTimeoutCalculator(Employee empl){
        int timeoutInSeconds = 120;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(empl.getSession_time().getTime());
        cal.add(Calendar.SECOND, timeoutInSeconds);
        return new Timestamp(cal.getTime().getTime());
    }
}

