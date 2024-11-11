package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;

@Entity
public class Participant {
    @Id
    @GeneratedValue
    private Long id;

    //TODO mora biti jedinstven
    private String username;

    private String password;

    //TODO mora biti jedinstven
    private String email;

    private String role;

    public Participant(String username,  String email) {
        this.username = username;
        this.email = email;
        this.role = RoleId.PARTICIPANT.name();
    }

    public Participant() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPresident() {
        return RoleId.LEAD.name().equals(role);
    }



}
