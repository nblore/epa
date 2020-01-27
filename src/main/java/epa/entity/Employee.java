package epa.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private Integer pin;
    private Timestamp session_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Timestamp getSession_time() {
        return session_time;
    }

    public void setSession_time(Timestamp session_time) {
        this.session_time = session_time;
    }
}