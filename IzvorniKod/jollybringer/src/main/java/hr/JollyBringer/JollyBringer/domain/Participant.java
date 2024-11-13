package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;
import org.springframework.util.Assert;

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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role_id;

    public Participant(String username,  String email, Role role) {
        Assert.hasText(username, "Username must have text");
        Assert.notNull(email, "E-mail must be set");
        Assert.notNull(email, "Role must be set");
        this.username = username;
        this.email = email;
        this.role_id= role;
        //this.role = new Role();
    }

    public Participant() {

    }

    public Role getRole() {
        return role_id;
    }

    public void setRole(Role role) {
        this.role_id = role;
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
        return "CHRISTMAS PRESIDENT".equals(role_id.getName());
    }



}
