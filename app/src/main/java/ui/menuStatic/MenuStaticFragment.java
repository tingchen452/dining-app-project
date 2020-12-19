package ui.menuStatic;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import JSONFiles.Location;
import JSONFiles.Meal;
import nwtft.dinningapp.R;
import ui.menuDynamic.SortedMeal;

public class MenuStaticFragment extends Fragment {

  private MenuStaticViewModel viewModel;

  private List<Location> menuData;
  private String todayDate;
  private int locationIndex;
  public RecyclerView rview;
  public TextView textMenu;


  public MenuStaticFragment() {
  }

  List<String> foodList = new ArrayList<String>();
  List<String> allergyList = new ArrayList<String>();


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(this).get(MenuStaticViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_menu_static, container, false);
    Context context = root.getContext();
    Bundle bundle = getArguments();
    if (bundle != null) {
      menuData = (List<Location>) getArguments().getSerializable("menuData");
      todayDate = getArguments().getString("Date");
      locationIndex = getArguments().getInt("Location", 0);
      todayDate = ("3/6/2020");
    }

    locationIndex = getArguments().getInt("Location", 0);

    textMenu = root.findViewById(R.id.textMenu);
    rview = root.findViewById(R.id.rview);


    // Since we never use this activity for for the Dining Commons, all of the other menus will
    // have an index of greater than 0. If 0 is reached, then there was an error.

    // Check if the menu corresponding to the index given from MainActivity is empty,
    // if so, then set the textView to "No menu available",
    // else set to the correct menu using both the locationIndex and the date that are passed as intents
    if (locationIndex == 0) {
      textMenu.append("There was an error parsing the menu");
    } else {
      if (menuData.get(locationIndex).getMenuCollection().getDayMenus().get(todayDate).getMeals()
              .size() > 0) {
        for (String mealTime : menuData.get(locationIndex).getMenuCollection().getDayMenus()
                .get(todayDate).getMeals().keySet()) {
          textMenu.append(mealTime + "\n");
          for (String category : menuData.get(locationIndex).getMenuCollection().getDayMenus()
                  .get(todayDate).getMeals().get(mealTime).getCategories().keySet()) {
            textMenu.append("       " + category + "\n");
            for (Meal food : menuData.get(locationIndex).getMenuCollection().getDayMenus()
                    .get(todayDate).getMeals().get(mealTime).getCategories().get(category)) {
              textMenu.append("              *" + food.getMealItem() + "\n");
              foodList.add(food.getMealItem());
              if (food.getAllergyItems().size() != 0) {
                for (int count = 0; count < food.getAllergyItems().size(); count++) {
                  textMenu
                          .append("                     -" + food.getAllergyItems().get(count) + "\n");
                }
              }


            }
          }
        }
        textMenu.setText("");
      } else {
        textMenu.append("There is no menu available for this location at this time");
      }
    }

    // List<Meal> genres = menuData.get(0).getMenuCollection().getDayMenus().get(date).getMeals();
    ArrayList<SortedMeal> m = new ArrayList<>();
    for (int x = 0; x < foodList.size(); x++) {
      m.add(x, new SortedMeal(foodList.get(x).toString()));
    }
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    Adapter2 adapter = new Adapter2(m);
    rview.setLayoutManager(layoutManager);
    rview.setAdapter(adapter);
    return root;
  }

}




