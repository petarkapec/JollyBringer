package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private String activity_name;

    private String description;

    private String date;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activity_status;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ParticipantGroup group;

    //TODO napraviti createdBy atribut i njegovu logiku
    private String createdBy;


    public Activity(String activity_name, String description, String date, ActivityStatus activity_status) {
        this.activity_name = activity_name;
        this.description = description;
        this.date = date;
        this.activity_status = activity_status;
    }

    public Activity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ActivityStatus getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(ActivityStatus activity_status) {
        this.activity_status = activity_status;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activity_name='" + activity_name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", activity_status='" + activity_status + '\'' +
                '}';
    }
}
