package hr.JollyBringer.JollyBringer.domain;


import jakarta.persistence.*;

@Entity
public class ApplicationRequest {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Participant user;

    private boolean isApplied;

    public ApplicationRequest(Participant userId, boolean isApplied) {
        this.isApplied = isApplied;
        this.user = userId;
    }

    public ApplicationRequest() {

    }

    public Participant getUser() {
        return user;
    }

    public void setUser(Participant user) {
        this.user = user;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
