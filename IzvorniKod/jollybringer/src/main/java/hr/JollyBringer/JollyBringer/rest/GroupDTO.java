package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupDTO {
    @JsonProperty("groupname")
    private String groupname;

    @JsonProperty("presidentEmail")
    private String presidentEmail;

    @JsonProperty("users")
    private List<String> users;

    // Getters and setters
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPresidentEmail() {
        return presidentEmail;
    }

    public void setPresidentEmail(String presidentEmail) {
        this.presidentEmail = presidentEmail;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}