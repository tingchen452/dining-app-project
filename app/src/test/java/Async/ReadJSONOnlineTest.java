package Async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import JSONFiles.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadJSONOnlineTest {

  ObjectMapper mapper = new ObjectMapper();
  List<Location> diningMenus;
  File file;

  @Before
  public void setUp() throws Exception {
    try {
      file = new File("src/test/java/Async/TestingMenu.json");
      assertNotNull(file);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      diningMenus = mapper.readValue(file, new TypeReference<List<Location>>() {
      });
      assertNotNull(diningMenus);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLength() {
    assertTrue(diningMenus.size() > 0);
    assertTrue(diningMenus.size() == 6);
  }

  // Some of the code relies on this order, so make sure that they are in that order
  @Test
  public void testOrder() {
    String[] names = {"Tim & Jeanne's Dining Commons",
        "Marketplace",
        "TJ Bistro",
        "Ely Harvest",
        "Wilson Café ",
        "Garden Café "};

    for (int i = 0; i < diningMenus.size(); i++) {
      assertEquals(names[i], diningMenus.get(i).getName());
    }
  }

  // Make sure locations have hours, at least one for each day
  @Test
  public void testHours() {
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        if (i < 4) {
          assertNotNull(diningMenus.get(i).getLocationInfo().getHours().get(days[j]).get(0));
        } else {
          assertNull(diningMenus.get(i).getLocationInfo());
        }
      }
    }
  }

  @Test
  public void testDescriptions() {
    for (int i = 0; i < diningMenus.size(); i++) {
      assertTrue(diningMenus.get(i).getDescription().length() > 0);
    }
  }

  @Test
  public void testURLs() {
    for (int i = 0; i < diningMenus.size(); i++) {
      try {
        URL url = new URL(diningMenus.get(i).getUrl());
        assertNotNull(url);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }
  }

  // test to make sure there are meals for each of the locations, except for Marketplace, Garden Cafe
  // just testing 1 category because assuming that if there is a category in the json, then it must have meals
  @Test
  public void testMeals() {

    // DC test
    assertNotNull(
        diningMenus.get(0).getMenuCollection().getDayMenus().get("3/6/2020").getMeals().get("Lunch")
            .getCategories());
    for (String cat : diningMenus.get(0).getMenuCollection().getDayMenus().get("3/6/2020")
        .getMeals().get("Lunch").getCategories().keySet()) {
      assertTrue(diningMenus.get(0).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
          .get("Lunch").getCategories().get(cat).size() > 0);
    }

    // Marketplace test
    assertTrue(
        diningMenus.get(1).getMenuCollection().getDayMenus().get("3/6/2020").getMeals().size()
            == 0);

    // Bistro test
    assertNotNull(diningMenus.get(2).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
        .get("Grab & Go").getCategories());
    for (String cat : diningMenus.get(2).getMenuCollection().getDayMenus().get("3/6/2020")
        .getMeals().get("Grab & Go").getCategories().keySet()) {
      assertTrue(diningMenus.get(2).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
          .get("Grab & Go").getCategories().get(cat).size() > 0);
    }

    // Ely Harvest test
    assertNotNull(diningMenus.get(3).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
        .get("Harvest in a Hurry").getCategories());
    for (String cat : diningMenus.get(3).getMenuCollection().getDayMenus().get("3/6/2020")
        .getMeals().get("Harvest in a Hurry").getCategories().keySet()) {
      assertTrue(diningMenus.get(3).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
          .get("Harvest in a Hurry").getCategories().get(cat).size() > 0);
    }

    // Wilson Cafe test
    assertNotNull(diningMenus.get(4).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
        .get("Wilson Cafe").getCategories());
    for (String cat : diningMenus.get(4).getMenuCollection().getDayMenus().get("3/6/2020")
        .getMeals().get("Wilson Cafe").getCategories().keySet()) {
      assertTrue(diningMenus.get(4).getMenuCollection().getDayMenus().get("3/6/2020").getMeals()
          .get("Wilson Cafe").getCategories().get(cat).size() > 0);
    }

    // Garden Cafe test
    assertTrue(
        diningMenus.get(5).getMenuCollection().getDayMenus().get("3/6/2020").getMeals().size()
            == 0);
  }

  @Test
  public void testAllergens() {
    // Only testing on 3/6/2020

    // Check that DC -> Lunch -> Speciality -> 3rd meal is both "Vegetable Fried Rice" and has 3 allergens
    assertEquals(diningMenus.get(0).getMenuCollection().getDayMenus().get("3/6/2020")
            .getMeals().get("Lunch").getCategories().get("Specialty").get(3).getMealItem(),
        "Vegetable Fried Rice");
    assertEquals(diningMenus.get(0).getMenuCollection().getDayMenus().get("3/6/2020")
            .getMeals().get("Lunch").getCategories().get("Specialty").get(3).getAllergyItems().size(),
        3);

    // Check that TJ Bistro -> Grab & Go -> Grab n'Go -> 1st meal is both "Buffalo Chicken Wrap" and has 0 allergens
    assertEquals(diningMenus.get(2).getMenuCollection().getDayMenus().get("3/6/2020")
            .getMeals().get("Grab & Go").getCategories().get("Grab n'Go").get(0).getMealItem(),
        "Buffalo Chicken Wrap");
    assertEquals(diningMenus.get(2).getMenuCollection().getDayMenus().get("3/6/2020")
        .getMeals().get("Grab & Go").getCategories().get("Grab n'Go").get(0).getAllergyItems()
        .size(), 0);

  }


  @After
  public void tearDown() {
    diningMenus = null;
    assertNull(diningMenus);
  }
}