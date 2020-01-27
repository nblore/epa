package epa.entity;

import java.sql.Timestamp;

public class EmployeeCardRegistration {

    private String id;
    private String cardId;
    private String name;
    private String email;
    private String mobile;
    private int pin;
    private Timestamp session_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Timestamp getSession_time() {
        return session_time;
    }

    public void setSession_time(Timestamp session_time) {
        this.session_time = session_time;
    }
}