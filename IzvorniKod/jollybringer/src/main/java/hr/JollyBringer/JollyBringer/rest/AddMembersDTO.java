package hr.JollyBringer.JollyBringer.rest;

import java.util.List;

public class AddMembersDTO {
    private List<Long> users;

    public AddMembersDTO() {}
    public AddMembersDTO(List<Long> users) {
        this.users = users;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
