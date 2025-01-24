package hr.JollyBringer.JollyBringer.rest;

public class ApplicationDTO {
    private Long user_id;
    private boolean applied;

    public ApplicationDTO(Long user_id, boolean applied) {
        this.user_id = user_id;
        this.applied = applied;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }
}
