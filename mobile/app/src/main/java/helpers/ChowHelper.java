package helpers;

import java.util.ArrayList;

import objects.Chows;

public class ChowHelper {
    public static ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("None");
        categories.add("Chinese");
        categories.add("American");
        categories.add("Italian");
        categories.add("Greek");
        return categories;
    }

    public static Chows ensureChowFields(Chows currentChow) {
        if (currentChow.getFood() == null) {
            currentChow.setFood("");
        }

        if (currentChow.getLastUpdated() == null) {
            currentChow.setLastUpdated("");
        }

        if (currentChow.getMeetLocation() == null) {
            currentChow.setMeetLocation("");
        }

        if (currentChow.getMeetTime() == null) {
            currentChow.setMeetTime("");
        }

        if (currentChow.getNotes() == null) {
            currentChow.setNotes("");
        }

        if (currentChow.getCategory() == null) {
            currentChow.setCategory("None");
        }

        if (currentChow.getPosterName() == null) {
            currentChow.setPosterName("");
        }

        if (currentChow.getPosterUser() == null) {
            currentChow.setPosterUser("");
        }

        if (currentChow.getPosterEmail() == null) {
            currentChow.setPosterEmail("");
        }

        return currentChow;
    }
}
