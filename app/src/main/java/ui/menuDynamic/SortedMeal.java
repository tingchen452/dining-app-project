package ui.menuDynamic;

import java.util.ArrayList;

public class SortedMeal {

    private String title;
    private ArrayList items;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private boolean ex;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ArrayList getItems() {
        return items;
    }

    public void setItems(ArrayList items) {
        this.items = items;
    }

    public boolean isEx() {
        return ex;
    }

    public void setEx(boolean ex) {
        this.ex = ex;
    }

    public SortedMeal(String title, ArrayList items, String location) {
        this.items = items;
        this.title = title;
        this.location = location;
        ex = false;

    }
    public SortedMeal(String title) {
        this.title = title;
        ex = false;

    }



    public String displayAllergy() {
        String s = "";
        if (items.size() != 0) {
            for (int x = 0; x < items.size(); x++) {
                s = s + items.get(x) + "  ";
            }
            return s;
        }
        else return "None";
    }

    @Override
    public String toString() {
        return "MealsN{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}

