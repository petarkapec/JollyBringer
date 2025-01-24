package hr.JollyBringer.JollyBringer.rest;

public class ActivityDTO {

    private String activity_name;
    private String description;
    private String date;
    private String activity_status; // Enum type as a String
    private Long group_id; // Matches group_id in JSON
    private String created_by;

    // Constructors
    public ActivityDTO() {
    }

    public ActivityDTO(String activity_name, String description, String date, String activity_status, Long group_id, String created_by) {
        this.activity_name = activity_name;
        this.description = description;
        this.date = date;
        this.activity_status = activity_status;
        this.group_id = group_id;
        this.created_by = created_by;
    }

    // Getters and Setters
    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(String activity_status) {
        this.activity_status = activity_status;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "ActivityDTO{" +
                "activity_name='" + activity_name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", activity_status='" + activity_status + '\'' +
                ", group_id=" + group_id +
                ", created_by='" + created_by + '\'' +
                '}';
    }
}
