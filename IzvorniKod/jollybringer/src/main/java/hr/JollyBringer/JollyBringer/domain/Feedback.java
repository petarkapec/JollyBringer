package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;
import org.springframework.util.Assert;

@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Enumerated(EnumType.STRING)
    private IsLiked isLiked = IsLiked.None; //default je none

    public Feedback(String comment, Activity activity, Participant participant) {
        Assert.hasText(comment, "Comment must have text");
        Assert.notNull(activity, "Activity must be set");
        Assert.notNull(participant, "Username must be set");
        this.comment = comment;
        this.activity = activity;
        this.participant = participant;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public IsLiked getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(IsLiked isLiked) {
        this.isLiked = isLiked;
    }

    public Feedback( String comment) {
        this.comment = comment;

    }

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", activity=" + activity +
                ", participant=" + participant +
                ", isLiked=" + isLiked +
                '}';
    }
}
