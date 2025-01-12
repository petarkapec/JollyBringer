package hr.JollyBringer.JollyBringer.rest;

public class FeedbackDTO {
    private Long activity_id;
    private Long participant_id;
    private String comment;
    private String is_liked;

    public FeedbackDTO() {
    }

    public FeedbackDTO(Long activityId, Long participantId, String comment, String isLiked) {
        this.activity_id = activityId;
        this.participant_id = participantId;
        this.comment = comment;
        this.is_liked = isLiked != null ? isLiked : "None";
    }

    public Long getActivityId() {
        return activity_id;
    }

    public void setActivityId(Long activityId) {
        this.activity_id = activityId;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public void setParticipantId(Long participantId) {
        this.participant_id = participantId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsLiked() {
        return is_liked;
    }

    public void setIsLiked(String isLiked) {
        this.is_liked = isLiked;
    }


    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "activityId=" + activity_id +
                ", participantId=" + participant_id +
                ", comment='" + comment + '\'';
    }
}
