package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private String activityName;

    private String description;

    private String date;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activity_status;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ParticipantGroup group;

    //TODO napraviti createdBy atribut i njegovu logiku
    private String createdBy;


    public Activity(String activity_name, String description, String date, String activity_status, ParticipantGroup group, String createdBy) {
        this.activityName = activity_name;
        this.description = description;
        this.date = date;
        this.activity_status = ActivityStatus.valueOf(activity_status);

        this.group = group;
        this.createdBy = createdBy;

    }

    public Activity() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activity_name) {
        this.activityName = activity_name;
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

    public ActivityStatus getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(String activity_status) {
        this.activity_status = ActivityStatus.valueOf(activity_status);
    }

    public void setActivity_status(ActivityStatus activity_status) {
        this.activity_status = activity_status;
    }

    public ParticipantGroup getGroup() {
        return group;
    }

    public void setGroup(ParticipantGroup group) {
        this.group = group;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", activity_status='" + activity_status + '\'' +
                '}';
    }
}
