package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;

@Entity
public class Participant {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    private String email;

    @OneToOne
    private Role role;

    private boolean isPresident;

}
