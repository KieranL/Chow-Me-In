package objects;

import java.io.Serializable;

public class Chows implements Serializable {

    private int id;           //a unique integer used to identify a chow,
    private boolean deleted;      //used for soft deletion of chows,
    private String food;         //a description of the food that is offered,
    private String lastUpdated;  //the last time the chow was updated in the format: "yyyy-mm-ddThh:mm:ss",
    private String meetLocation; //a description of where to meet in person,
    private String meetTime;     //the time to meet in the format: "yyyy-mm-ddThh:mm:ss",
    private String notes;        //any additional notes the poster of the chow may want to share
    private String posterName;
    private String posterUser;
    private String posterEmail;
    private String category;

    public Chows() {
    }

    public int getId() {
        return id;
    }

    public Chows setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Chows setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getFood() {
        return food;
    }

    public Chows setFood(String food) {
        this.food = food;
        return this;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public Chows setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public Chows setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
        return this;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public Chows setMeetTime(String meetTime) {
        this.meetTime = meetTime;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Chows setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getPosterName() {
        return posterName;
    }

    public Chows setPosterName(String posterName) {
        this.posterName = posterName;
        return this;
    }

    public String getPosterUser() {
        return posterUser;
    }

    public Chows setPosterUser(String posterUser) {
        this.posterUser = posterUser;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Chows setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getPosterEmail() {
        return posterEmail;
    }

    public Chows setPosterEmail(String posterEmail) {
        this.posterEmail = posterEmail;
        return this;
    }



    @Override
    public String toString() {
        return "Chow name: " + getFood() + "\nCategory: " + getCategory() + "\nMeet Location: " + getMeetLocation() + "\nMeet Time: " + getMeetTime() + "\nAdditional Notes: " + getNotes() + "\nContact Name: " + getPosterName();
    }

}


