package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
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

    public ParticipantGroup(String name, Participant president) {
        Assert.hasText(name, "Group name must have text");
        Assert.isTrue(name.length() <= 20,
                "Group name max length is 20 characters, got: " + name); //Necesarry?
        Assert.notNull(president, "Christmas president must be set");
        this.name = name;
        this.president = president;
        this.members = new HashSet<>(Arrays.asList(president));
    }

    public ParticipantGroup() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 1, max = 20) String getName() {
        return name;
    }

    public void setName(@Size(min = 1, max = 20) String name) {
        this.name = name;
    }

    public Participant getPresident() {
        return president;
    }

    public void setPresident(Participant president) {
        this.president = president;
    }

    public Set<Participant> getMembers() {
        return members;
    }

    public void setMembers(Set<Participant> members) {
        this.members = members;
    }
}
