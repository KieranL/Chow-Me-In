package objects;

/*
Example DynamoDB entry
  "chowId": 1,
  "createdBy": 420,
  "createdTime": "2018-02-08T13:36:00",
  "deleted": false,
  "food": "Mom's spaghetti",
  "lastUpdated": "2018-02-08T13:36:01",
  "meetLocation": "Da hood",
  "meetTime": "Whenever yo",
  "notes": "Hit me up"
 */

public class Chows {
    private int chowID ;
    private int createdBy ;
    private String createdTime;
    private boolean deleted;
    private String food;
    private String lastUpdated;
    private String meetLocation;
    private String meetTime;
    private String notes;

    public Chows(int chowID, int createdBy, String createdTime, boolean deleted, String food, String lastUpdated, String meetLocation, String meetTime, String notes) {
        this.chowID = chowID;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.deleted = deleted;
        this.food = food;
        this.lastUpdated = lastUpdated;
        this.meetLocation = meetLocation;
        this.meetTime = meetTime;
        this.notes = notes;
    }

    public int getChowID() {
        return chowID;
    }

    public void setChowID(int chowID) {
        this.chowID = chowID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
