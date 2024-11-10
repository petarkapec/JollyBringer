package hr.JollyBringer.JollyBringer.rest;

public class CreateGroupDTO {
    private String name;
    private String leadUsername;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadUsername() {
        return leadUsername;
    }

    public void setLeadUsername(String leadUsername) {
        this.leadUsername = leadUsername;
    }
}
