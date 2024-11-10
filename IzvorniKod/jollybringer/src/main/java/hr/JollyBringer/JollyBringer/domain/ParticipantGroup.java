package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class ParticipantGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true, nullable=false)
    @Size(min=1, max=20)
    private String name;

    @OneToOne
    private Participant president;

    @OneToMany
    private Set<Participant> members;

}
