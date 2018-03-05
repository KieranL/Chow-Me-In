package business;

import java.util.ArrayList;

public class ChowHandler {
    public static ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("None");
        categories.add("Chinese");
        categories.add("American");
        categories.add("Italian");
        categories.add("Greek");
        return categories;
    }
}
