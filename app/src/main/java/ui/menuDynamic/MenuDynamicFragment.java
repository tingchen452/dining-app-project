package ui.menuDynamic;

import JSONFiles.Location;
import JSONFiles.Meal;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nwtft.dinningapp.R;

public class MenuDynamicFragment extends Fragment implements OnItemSelectedListener {

  final String[] allergy = {"Sustainable", "Vegetarian", "Vegan", "Local Produce", "Whole Grain",
      "Contains Nuts"};
  public List<Location> menuData = null;
  public TextView textMenu;
  public Spinner spin1;
  public Spinner spin2;
  public RecyclerView rview;
  public LinearLayoutManager layoutManager;
  public TextView textConnection;
  String todayDate;
  int locationIndex;
  Context context;
  boolean[] itemSelected;
  ArrayList<DynamicAdapter> m=new ArrayList<>();
  DynamicAdapter adapter;
  boolean network;

  private MenuDynamicViewModel viewModel;


  public MenuDynamicFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(this).get(MenuDynamicViewModel.class);
    final View root = inflater.inflate(R.layout.fragment_menu_dynamic, container, false);

    context = root.getContext();

    Bundle bundle = getArguments();


    if (bundle != null) {
      menuData = (List<Location>) bundle.getSerializable("menuData");
      todayDate = bundle.getString("Date");
      todayDate=("3/5/2020");
      network = bundle.getBoolean("Network");
    }

    textMenu = root.findViewById(R.id.textMenu);
    spin1 = root.findViewById(R.id.spinner);
    spin2 = root.findViewById(R.id.spinner2);
    rview = root.findViewById(R.id.rview);
    String[] parts = null;
    textConnection = root.findViewById(R.id.noConnectionText);

    if (!network) {
      rview.setVisibility(View.GONE);
      textConnection.setVisibility(View.VISIBLE);
    }
    // if network connection is regained after app is started
    else if (network) {
      rview.setVisibility(View.VISIBLE);
      textConnection.setVisibility(View.GONE);
    }


    ArrayList<String> day = new ArrayList<String>(
        menuData.get(0).getMenuCollection().getDayMenus().keySet());
    //convert to yyyy/mm/dd for sort
    for (int x = 0; x < day.size(); x++) {
      parts = day.get(x).split("/");
      if (parts[0].length() == 1) {
        parts[0] = "0" + parts[0];
      }
      if (parts[1].length() == 1) {
        parts[1] = "0" + parts[1];
      }
      day.set(x, parts[2] + "/" + parts[0] + "/" + parts[1]);
    }
    Collections.sort(day);
    //convert mm/dd/yyyy for display
    for (int x = 0; x < day.size(); x++) {
      parts = day.get(x).split("/");
      day.set(x, parts[1] + "/" + parts[2] + "/" + parts[0]);
    }

    ArrayAdapter dataAdapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_dropdown_item, day);
    spin1.setAdapter(dataAdapter);

    ArrayList<String> meal = new ArrayList<String>(
        menuData.get(0).getMenuCollection().getDayMenus().get(todayDate).getMeals().keySet());
    meal.add(0, "All Meals");
    ArrayAdapter mealAdapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_dropdown_item, meal);
    spin2.setAdapter(mealAdapter);

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    spin1.setOnItemSelectedListener(this);
    spin2.setOnItemSelectedListener(this);
    final Button but = root.findViewById(R.id.button);

    but.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select one, many, or none: ");

        builder
            .setMultiChoiceItems(allergy, null, new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                itemSelected = new boolean[allergy.length];
                itemSelected[which] = isChecked;
              }
            });

        //Creating CANCEL button in alert dialog, to dismiss the dialog box when nothing is selected
        builder.setCancelable(false)
            .setNegativeButton("Apply", new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int id) {
                //When clicked on CANCEL button the dalog will be dismissed

                displayFoodData(fixDate(spin1.getSelectedItem().toString()),
                    spin2.getSelectedItem().toString(), itemSelected);
                itemSelected = null;
                dialog.dismiss();


              }
            });
        AlertDialog alert = builder.create();
        alert.show();


      }
    });

    SearchView searchBar=root.findViewById(R.id.search_menu_dynamic);
    searchBar.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
      }
    });

    return root;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    switch (parent.getId()) {
      case R.id.spinner:
        String item = parent.getItemAtPosition(position).toString();
        displayFoodData(fixDate(item), spin2.getSelectedItem().toString(), itemSelected);
        ArrayList<String> meal = new ArrayList<String>(
            menuData.get(0).getMenuCollection().getDayMenus().get(fixDate(item)).getMeals()
                .keySet());
        meal.add(0, "All Meals");
        ArrayAdapter mealAdapter = new ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_dropdown_item, meal);
        spin2.setAdapter(mealAdapter);

        break;
      case R.id.spinner2:
        String item2 = parent.getItemAtPosition(position).toString();
        displayFoodData(fixDate(spin1.getSelectedItem().toString()), item2, itemSelected);
        break;
    }

  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    textMenu.setText("Error");
  }

  public void displayFoodData(String date, String meal, boolean[] selected) {
    ArrayList<String> list = algSelected(selected);
    //textMenu.setText("");

    List station = new ArrayList<String>();
    List foodList = new ArrayList<String>();
    List allergy = new ArrayList<List>();


    for (String mealTime : menuData.get(0).getMenuCollection().getDayMenus().get(date).getMeals()
        .keySet()) {
      if (meal != "All Meals" && mealTime != meal) {
        continue;
      }
      //textMenu.append(mealTime + "\n");
      for (String category : menuData.get(0).getMenuCollection().getDayMenus().get(date).getMeals()
          .get(mealTime).getCategories().keySet()) {
        //textMenu.append("       " + category + "\n");
        for (Meal food : menuData.get(0).getMenuCollection().getDayMenus().get(date).getMeals()
            .get(mealTime).getCategories().get(category)) {
          if (list != null) {
            for (int x = 0; x < list.size(); x++) {
              for (int y = 0; y < food.getAllergyItems().size(); y++) {
                if (list.get(x).equals(food.getAllergyItems().get(y).toString())) {
                  //textMenu.append("              *" + food.getMealItem() + "\n");
                  station.add(category);
                  foodList.add(food.getMealItem());
                  if (food.getAllergyItems().size() != 0) {
                    allergy.add(food.getAllergyItems());
                    for (int count = 0; count < food.getAllergyItems().size(); count++) {
                      //textMenu.append(
                       //   "                     -" + food.getAllergyItems().get(count) + "\n");
                    }
                  }
                  else
                  {
                    allergy.add(new ArrayList<>());
                  }
                }
              }

            }
          } else {
            //textMenu.append("              *" + food.getMealItem() + "\n");
              station.add(category);
            foodList.add(food.getMealItem());
            if (food.getAllergyItems().size() != 0) {
              allergy.add(food.getAllergyItems());
              for (int count = 0; count < food.getAllergyItems().size(); count++) {
                //textMenu
                 //   .append("                     -" + food.getAllergyItems().get(count) + "\n");
              }
            }
            else{
              allergy.add(new ArrayList<>());
            }
          }
        }
      }
    }

   // List<Meal> genres = menuData.get(0).getMenuCollection().getDayMenus().get(date).getMeals();
    ArrayList<SortedMeal> m = new ArrayList<>();
  for(int x =0; x<foodList.size(); x++){
    m.add(x, new SortedMeal(foodList.get(x).toString(), (ArrayList) allergy.get(x), (String) station.get(x)));
  }
    //textMenu.setText("");
   // ArrayList<SortedMeal> m = new ArrayList<>();
    layoutManager = new LinearLayoutManager(context);
    adapter = new DynamicAdapter(m);
    rview.setLayoutManager(layoutManager);
    rview.setAdapter(adapter);


  }

  public String fixDate(String date) {
    //convert back to JSON style to be readable
    String[] parts = null;
    parts = date.split("/");
    if (parts[1].substring(0, 1).equals("0")) {
      parts[1] = parts[1].substring(1);
    }
    if (parts[0].substring(0, 1).equals("0")) {
      parts[0] = parts[0].substring(1);
    }
    date = parts[0] + "/" + parts[1] + "/" + parts[2];
    return date;

  }

  public ArrayList<String> algSelected(boolean[] list) {
    ArrayList<String> newList = new ArrayList<>();
    if (list == null) {
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      if (list[i] == true) {
        newList.add(allergy[i]);
      }

    }

    return newList;
  }
}