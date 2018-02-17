package objects;

/*
Example DynamoDB entry
  "id": 1,
  "createdBy": 420,
  "createdTime": "2018-02-08T13:36:00",
  "deleted": false,
  "food": "Mom's spaghetti",
  "lastUpdated": "2018-02-08T13:36:01",
  "meetLocation": "Da hood",
  "meetTime": "Whenever yo",
  "notes": "Hit me up"
 */

import java.io.Serializable;

public class Chows implements Serializable{

    private int id;
    private int createdBy;
    private String createdTime;
    private boolean deleted;
    private String food;
    private String lastUpdated;
    private String meetLocation;
    private String meetTime;
    private String notes;

    public Chows() {
    }

    public Chows(int id, int createdBy, String createdTime, boolean deleted, String food, String lastUpdated, String meetLocation, String meetTime, String notes) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.deleted = deleted;
        this.food = food;
        this.lastUpdated = lastUpdated;
        this.meetLocation = meetLocation;
        this.meetTime = meetTime;
        this.notes = notes;
    }

    public int getid() {
        return id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getFood() {
        return food;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}


