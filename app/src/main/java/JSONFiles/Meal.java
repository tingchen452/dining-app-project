package JSONFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable {

  private String mealItem;
  private List allergyItems;

  public Meal() {

  }

  public String getMealItem() {
    return mealItem;
  }

  public void setMealItem(String mealItem) {
    this.mealItem = mealItem;
  }

  public List getAllergyItems() {
    return allergyItems;
  }

  public void setAllergyItems(ArrayList<String> allergyItems) {
    this.allergyItems = allergyItems;
  }

  public void addFilters(String menuItem, String filters) {

  }
}
