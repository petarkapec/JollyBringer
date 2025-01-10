package hr.JollyBringer.JollyBringer.rest;

public class ActivityDTO {

    private String ACTIVITY_NAME;
    private String DESCRIPTION;
    private String DATE;
    private String ACTIVITY_STATUS; // Enum type as a String
    private Long GROUP_ID; // Matches GROUP_ID in JSON
    private String CREATED_BY;

    // Constructors
    public ActivityDTO() {
    }

    public ActivityDTO(String ACTIVITY_NAME, String DESCRIPTION, String DATE, String ACTIVITY_STATUS, Long GROUP_ID, String CREATED_BY) {
        this.ACTIVITY_NAME = ACTIVITY_NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.DATE = DATE;
        this.ACTIVITY_STATUS = ACTIVITY_STATUS;
        this.GROUP_ID = GROUP_ID;
        this.CREATED_BY = CREATED_BY;
    }

    // Getters and Setters
    public String getACTIVITY_NAME() {
        return ACTIVITY_NAME;
    }

    public void setACTIVITY_NAME(String ACTIVITY_NAME) {
        this.ACTIVITY_NAME = ACTIVITY_NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getACTIVITY_STATUS() {
        return ACTIVITY_STATUS;
    }

    public void setACTIVITY_STATUS(String activity_status) {
        this.ACTIVITY_STATUS = activity_status;
    }

    public Long getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(Long GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "ActivityDTO{" +
                "ACTIVITY_NAME='" + ACTIVITY_NAME + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", DATE='" + DATE + '\'' +
                ", activity_status='" + ACTIVITY_STATUS + '\'' +
                ", GROUP_ID=" + GROUP_ID +
                ", CREATED_BY='" + CREATED_BY + '\'' +
                '}';
    }
}
