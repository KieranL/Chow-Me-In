package objects;

import java.io.Serializable;

public class Chows implements Serializable {

    private int id;           //a unique integer used to identify a chow,
    private String createdBy;    //an integer that corresponds to a user,
    private String createdTime;  //the time the chow was created in the format: "yyyy-mm-ddThh:mm:ss",
    private boolean deleted;      //used for soft deletion of chows,
    private String food;         //a description of the food that is offered,
    private String lastUpdated;  //the last time the chow was updated in the format: "yyyy-mm-ddThh:mm:ss",
    private String meetLocation; //a description of where to meet in person,
    private String meetTime;     //the time to meet in the format: "yyyy-mm-ddThh:mm:ss",
    private String notes;        //any additional notes the poster of the chow may want to share
    private String posterEmail;
    private String posterName;
    private String posterUser;
    private String category;

    public Chows() {
    }

    public Chows(int id, String createdBy, String createdTime, boolean deleted, String food, String lastUpdated, String meetLocation, String meetTime, String notes, String category, String posterName, String posterUser, String posterEmail) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.deleted = deleted;
        this.food = food;
        this.lastUpdated = lastUpdated;
        this.meetLocation = meetLocation;
        this.meetTime = meetTime;
        this.notes = notes;
        this.category = category;
        this.posterEmail = posterEmail;
        this.posterName = posterName;
        this.posterUser = posterUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
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

    public String getPosterEmail() {
        return posterEmail;
    }

    public void setPosterEmail(String posterEmail) {
        this.posterEmail = posterEmail;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterUser() {
        return posterUser;
    }

    public void setPosterUser(String posterUser) {
        this.posterUser = posterUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Chow name: " + getFood() + "\nCategory: " + getCategory() + "\nMeet Location: " + getMeetLocation() + "\nMeet Time: " + getMeetTime() + "\nAdditional Notes: " + getNotes() + "\nContact Name: " + getPosterName() + "\nContact Phone: " + getPosterEmail();
    }

}


