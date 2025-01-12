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

    public Long getActivity_id() {
        return activity_id;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public String getComment() {
        return comment;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public void setParticipant_id(Long participant_id) {
        this.participant_id = participant_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "activityId=" + activity_id +
                ", participantId=" + participant_id +
                ", comment='" + comment + '\'';
    }
}
