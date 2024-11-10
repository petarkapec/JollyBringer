package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id

    private Long id;

    private String name;
}
