package nwtft.dinningapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ui.events.EventFragment;
import ui.feedback.FeedbackFragment;
import ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BottomNavigationView navView = findViewById(R.id.nav_view);

    Fragment homeFragment = new HomeFragment();
    Fragment eventFragment = new EventFragment();
    Fragment feedbackFragment = new FeedbackFragment();

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        homeFragment.getId(), eventFragment.getId(), feedbackFragment.getId())
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupWithNavController(navView, navController);
  }
}
