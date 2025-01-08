package hr.JollyBringer.JollyBringer.rest;

public class ActivityDTO {

    private String activityName;
    private String description;
    private String date;
    private String activity_status; // Enum type as a String
    private Long group_id; // Matches group_id in JSON
    private String createdBy;

    // Constructors
    public ActivityDTO() {
    }

    public ActivityDTO(String activityName, String description, String date, String activity_status, Long group_id, String createdBy) {
        this.activityName = activityName;
        this.description = description;
        this.date = date;
        this.activity_status = activity_status;
        this.group_id = group_id;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "ActivityDTO{" +
                "activityName='" + activityName + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", activity_status='" + activity_status + '\'' +
                ", group_id=" + group_id +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
