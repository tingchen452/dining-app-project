package JSONFiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Collection of day dayMenus from multiple days from a particular dining location Each DayMenu can
 * be retrieved using the calendar date in the format of YYYY-MM-DD
 */
public class MenuCollection implements Serializable {

  HashMap<String, DayMenu> dayMenus = new HashMap<>(); // map a date DD/MM/YYYY to a day menu

  /**
   * Default constructor, required by JSON deserializer
   */
  public MenuCollection() {
  }

  public MenuCollection(String diningLocationMenuUrl) {

  }

  public HashMap<String, DayMenu> getDayMenus() {
    return dayMenus;
  }

  @Override
  public String toString() {
    return "MenuCollection{" +
        "dayMenus=" + dayMenus +
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
    MenuCollection that = (MenuCollection) o;
    return Objects.equals(dayMenus, that.dayMenus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayMenus);
  }
}
