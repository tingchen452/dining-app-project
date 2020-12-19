package JSONFiles;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collection of categories and a list of items (recipes/food name) in the category
 */
public class MealMenu implements Serializable {

  private Map<String, List<Meal>> categories = new HashMap<>();

  static private Pattern removePattern = Pattern.compile("(?:--)\\s*(.+)\\s*(?:--)");

  /**
   * Default constructor, required by JSON deserializer
   */
  public MealMenu() {
  }

  private static String cleanUpName(String name) {
    Matcher matcher = removePattern.matcher(name);
    String retval = name;
    if (matcher.find()) {
      retval = matcher.group(1);
    }
    // trim doesn't trim nbsp (char code 160)
    return retval.replace((char) 0xA0, ' ').trim();
  }

  public void addCategory(String categoryName) {
    String cleanCategoryName = cleanUpName(categoryName);
    assert categories.get(cleanCategoryName) == null;
    categories.put(cleanCategoryName, new ArrayList<Meal>());

  }

  public void addItem(String categoryName, Meal menuObj) {
    String cleanCategoryName = cleanUpName(categoryName);
    String menuItem = menuObj.getMealItem();
    String cleanItem = cleanUpName(menuItem);
    menuObj.setMealItem(cleanItem);
    if (null == categories.get(cleanCategoryName)) {
      addCategory(cleanCategoryName);
    }
    categories.get(cleanCategoryName).add(menuObj);
  }

  public Map<String, List<Meal>> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    return "MealMenu{" +
        "categories=" + categories +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MealMenu mealMenu = (MealMenu) o;
    return Objects.equals(categories, mealMenu.categories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categories);
  }
}
