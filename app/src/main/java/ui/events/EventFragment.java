package ui.events;

import JSONFiles.Location;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.List;
import nwtft.dinningapp.R;

public class EventFragment extends Fragment {

  private EventViewModel viewModel;

  private String todayDate;
  private List<Location> menuData;
  private String dayLongName;
  private String formattedDate;

  private TextView[] textViewEventName;
  private TextView[] textViewDate;
  private TextView[] textViewDescriptions;
  private ImageView[] imageViews;

  private int locationIndex;

  private Bundle savedState = null;

  public EventFragment() {
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_events, container, false);

    /*  9 cards but cards start at 1
            Card layout: where X is the card number
                textX_1     -> Event name
                textX_2     -> Date
                textX_3     -> Description
    */

    // populate arrays with the associated items

    textViewEventName = new TextView[]{root.findViewById(R.id.text1_1A),
        root.findViewById(R.id.text2_1A),
        root.findViewById(R.id.text3_1A),
        root.findViewById(R.id.text4_1A),
        root.findViewById(R.id.text5_1A),
        root.findViewById(R.id.text6_1A),
        root.findViewById(R.id.text7_1A),
        root.findViewById(R.id.text8_1A),
        root.findViewById(R.id.text9_1A)};

    textViewDate = new TextView[]{root.findViewById(R.id.text1_2A),
        root.findViewById(R.id.text2_2A),
        root.findViewById(R.id.text3_2A),
        root.findViewById(R.id.text4_2A),
        root.findViewById(R.id.text5_2A),
        root.findViewById(R.id.text6_2A),
        root.findViewById(R.id.text7_2A),
        root.findViewById(R.id.text8_2A),
        root.findViewById(R.id.text9_2A)};

    textViewDescriptions = new TextView[]{root.findViewById(R.id.text1_3A),
        root.findViewById(R.id.text2_3A),
        root.findViewById(R.id.text3_3A),
        root.findViewById(R.id.text4_3A),
        root.findViewById(R.id.text5_3A),
        root.findViewById(R.id.text6_3A),
        root.findViewById(R.id.text7_3A),
        root.findViewById(R.id.text8_3A),
        root.findViewById(R.id.text9_3A)};

    imageViews = new ImageView[]{root.findViewById(R.id.image1),
        root.findViewById(R.id.image2),
        root.findViewById(R.id.image3),
        root.findViewById(R.id.image4),
        root.findViewById(R.id.image5),
        root.findViewById(R.id.image6),
        root.findViewById(R.id.image7),
        root.findViewById(R.id.image8),
        root.findViewById(R.id.image9)};

    return root;

  }

}

