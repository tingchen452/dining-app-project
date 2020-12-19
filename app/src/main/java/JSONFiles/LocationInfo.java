package JSONFiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LocationInfo implements Serializable {

  private Map<String, List<String>> hours = new HashMap<>();

  public LocationInfo() {
  }

  public Map<String, List<String>> getHours() {
    return hours;
  }

  public void setHours(Map<String, List<String>> hours) {
    this.hours = hours;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationInfo that = (LocationInfo) o;
    return Objects.equals(hours, that.hours);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hours);
  }

  @Override
  public String toString() {
    return "LocationInfo{" +
        "hours=" + hours +
        '}';
  }
}
