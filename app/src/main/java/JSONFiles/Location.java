package JSONFiles;

import java.io.Serializable;
import java.util.Objects;

/**
 * Dining Location
 */
public class Location implements Serializable {

  // URL prefix for all of the
  public static final String URL_PREFIX = "http://www.westfield.ma.edu/diningservices/foodpro.net/";

  private String name;
  private String description;
  private String url;
  private MenuCollection menuCollection;
  private LocationInfo locationInfo;

  /**
   * Default constructor, required by JSON deserializer
   */
  public Location() {
  }

  public void populateMenuCollection() throws Exception {
    if (null == url) {
      throw new Exception("Need to set the url first before populating menu collection");
    }

    menuCollection = new MenuCollection(url);
  }

  public MenuCollection getMenuCollection() {
    return menuCollection;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public LocationInfo getLocationInfo() {
    return locationInfo;
  }

  public void setLocationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
  }

  @Override
  public String toString() {
    return "Location{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", url='" + url + '\'' +
        ", menuCollection=" + menuCollection +
        ", locationInfo=" + locationInfo +
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
    Location location = (Location) o;
    return Objects.equals(name, location.name) &&
        Objects.equals(description, location.description) &&
        Objects.equals(url, location.url) &&
        Objects.equals(menuCollection, location.menuCollection) &&
        Objects.equals(locationInfo, location.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, url, menuCollection, locationInfo);
  }
}
