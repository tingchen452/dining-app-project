package ui.home;

import Async.ConnectionSingleton;
import JSONFactory.GetJSON;
import JSONFactory.GetJSONFactory;
import JSONFiles.Location;
import Network.AnalyzeNetworkConnection;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import nwtft.dinningapp.R;

public class  HomeFragment extends Fragment {

  private HomeViewModel viewModel;

  private String todayDate;
  private List<Location> menuData;
  private String dayLongName;
  private String formattedDate;

  private TextView[] textViewHours;
  private TextView[] textViewDescriptions;
  private Button[] viewMenuButtons;
  private Button[] viewMapButtons;
  private ImageView[] imageViews;

  private int locationIndex;

  private Bundle savedState = null;

  public HomeFragment() {
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_home, container, false);
    /*  6 cards but cards start at 1
            Card layout: where X is the card number
                textX_1     -> Location name
                textX_2     -> Location hours
                textX_3     -> Description
                buttonX_1   -> Go to menu button
                buttonX_2   -> More info button
    */

    // populate arrays with the associated items
    textViewHours = new TextView[]{root.findViewById(R.id.text1_2),
            root.findViewById(R.id.text2_2),
            root.findViewById(R.id.text3_2),
            root.findViewById(R.id.text4_2),
            root.findViewById(R.id.text5_2),
            root.findViewById(R.id.text6_2)};

    textViewDescriptions = new TextView[]{root.findViewById(R.id.text1_3),
            root.findViewById(R.id.text2_3),
            root.findViewById(R.id.text3_3),
            root.findViewById(R.id.text4_3),
            root.findViewById(R.id.text5_3),
            root.findViewById(R.id.text6_3)};

    viewMenuButtons = new Button[]{root.findViewById(R.id.button1_1),
            root.findViewById(R.id.button2_1),
            root.findViewById(R.id.button3_1),
            root.findViewById(R.id.button4_1),
            root.findViewById(R.id.button5_1),
            root.findViewById(R.id.button6_1)};

    viewMapButtons = new Button[]{root.findViewById(R.id.button1_2),
            root.findViewById(R.id.button2_2),
            root.findViewById(R.id.button3_2),
            root.findViewById(R.id.button4_2),
            root.findViewById(R.id.button5_2),
            root.findViewById(R.id.button6_2)};
    imageViews = new ImageView[]{root.findViewById(R.id.image1),
            root.findViewById(R.id.image2),
            root.findViewById(R.id.image3),
            root.findViewById(R.id.image4),
            root.findViewById(R.id.image5),
            root.findViewById(R.id.image6)};

    // use Picasso library in order to display and manage the images
    String[] imageNames = new String[]{"tj_dining_commons",
            "marketplace",
            "tj_bistro",
            "ely_harvest",
            "wilson_cafe",
            "garden_cafe"};

    for (int i = 0; i < 6; i++) {
      int drawID = getContext().getResources()
              .getIdentifier(imageNames[i], "drawable", getActivity().getPackageName());
      Picasso.get().load(drawID).resize(400, 300).centerCrop().into(imageViews[i]);
    }

    // set descriptions
    for (int i = 0; i < textViewDescriptions.length; i++) {
      if (menuData.get(i).getDescription() != null) {
        textViewDescriptions[i].setText(menuData.get(i).getDescription());
      } else {
        textViewDescriptions[i].setText("There is no description for this location");
      }
    }

    // set hours
    for (int i = 0; i < textViewHours.length; i++) {
      if (menuData.get(i).getLocationInfo() != null) {
        List<String> locationHours = menuData.get(i).getLocationInfo().getHours().get(dayLongName);
        textViewHours[i].append(locationHours.get(0));
        for (int j = 1; j < locationHours.size(); j++) {
          textViewHours[i].append("\n                         " + locationHours.get(j));
        }
      } else {
        textViewHours[i].setText("There are no hours available for this location");
      }
    }
    // Only distinction between this listener and the activity one is that this one uses
    // the navigation controller that is initialized in the MainActivity and is ever-present
    // navigation is done through Actions accessed by their id
    View.OnClickListener viewMenuListener = new View.OnClickListener() {

      private Bundle bundle = new Bundle();

      @Override
      public void onClick(View v) {
        bundle.putString("Date", formattedDate);
        bundle.putSerializable("menuData", (Serializable) menuData);

        AnalyzeNetworkConnection netConnection = ConnectionSingleton.getNetworkAnalzyer();
        boolean isConnected = netConnection.checkNetworkConnection(root.getContext());

        bundle.putBoolean("Network", isConnected);

        switch (v.getId()) {
          case R.id.button1_1:
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuDynamic, bundle);
            break;
          case R.id.button2_1:
            bundle.putInt("Location", 1);
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuStatic, bundle);
            break;
          case R.id.button3_1:
            bundle.putInt("Location", 2);
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuStatic, bundle);
            break;
          case R.id.button4_1:
            bundle.putInt("Location", 3);
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuStatic, bundle);
            break;
          case R.id.button5_1:
            bundle.putInt("Location", 4);
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuStatic, bundle);
            break;
          case R.id.button6_1:
            bundle.putInt("Location", 5);
            Navigation.findNavController(root).navigate(R.id.action_home_to_menuStatic, bundle);
            break;
        }
      }
    };


  View.OnClickListener viewMapListener = new View.OnClickListener() {

    private Bundle bundle = new Bundle();

    @Override
    public void onClick(View v) {
      bundle.putSerializable("menuData", (Serializable) menuData);
      switch (v.getId()) {
        case R.id.button1_2:
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
        case R.id.button2_2:
          bundle.putInt("Location", 1);
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
        case R.id.button3_2:
          bundle.putInt("Location", 2);
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
        case R.id.button4_2:
          bundle.putInt("Location", 3);
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
        case R.id.button5_2:
          bundle.putInt("Location", 4);
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
        case R.id.button6_2:
          bundle.putInt("Location", 5);
          Navigation.findNavController(root).navigate(R.id.action_home_to_map, bundle);
          break;
      }
    }
  };

    // assign custom event listener to each button that will open the menu
    for (int i = 0; i < viewMenuButtons.length; i++) {
      viewMenuButtons[i].setOnClickListener(viewMenuListener);
    }
    for (int i = 0; i < viewMapButtons.length; i++) {
    viewMapButtons[i].setOnClickListener(viewMapListener);
  }

    return root;
}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AnalyzeNetworkConnection networkConnection = ConnectionSingleton.getNetworkAnalzyer();

    boolean isConnected = networkConnection.checkNetworkConnection(this.getContext());

    GetJSON getJSON = null;
    GetJSONFactory jsonFactory = new GetJSONFactory();

    if (isConnected) {
      getJSON = jsonFactory.getJSONFile("ONLINE");
    } else {
      getJSON = jsonFactory.getJSONFile("OFFLINE");
    }

    menuData = getJSON.getDiningMenu(this.getContext());


    Calendar sCalendar = Calendar.getInstance();
    dayLongName = sCalendar
        .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);

    // Get today's date in M/d/YYYY format eg. 2/21/2020
    Date currentDate = sCalendar.getTime();
    SimpleDateFormat df = new SimpleDateFormat("M/d/YYYY");
    formattedDate = df.format(currentDate);

  }

}
