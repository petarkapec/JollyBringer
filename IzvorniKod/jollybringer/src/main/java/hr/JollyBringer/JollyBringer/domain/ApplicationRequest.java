package hr.JollyBringer.JollyBringer.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ApplicationRequest {
    @Id
    private Long userId;

    private boolean isApplied;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }
}
