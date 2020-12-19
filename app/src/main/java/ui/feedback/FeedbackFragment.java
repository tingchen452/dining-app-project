package ui.feedback;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.List;
import nwtft.dinningapp.R;

public class FeedbackFragment extends Fragment {

  private FeedbackViewModel viewModel;
  private Spinner sItems;
  private EditText emailText;
  private EditText emailAd;
  private EditText pass;
  private Button emailButton;


  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_feedback, container, false);

    emailButton = root.findViewById(R.id.send_email);
    sItems = root.findViewById(R.id.spinner4);
    emailText = root.findViewById(R.id.message);
    List<String> spinnerArray =  new ArrayList<String>();
    spinnerArray.add("Select a Location...");
    spinnerArray.add("General");
    spinnerArray.add("Tim & Jeanne's Dining Commons");
    spinnerArray.add("Ely Harvest");
    spinnerArray.add("T.J. Bistro");
    spinnerArray.add("Market Place");
    spinnerArray.add("Wilson Cafe");
    spinnerArray.add("Garden Cafe");


    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray){
      @Override
      public boolean isEnabled(int position){
        return position != 0;
      }
      @Override
      public View getDropDownView(int position, View convertView,
          ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
          // Set the hint text color gray
          tv.setTextColor(Color.GRAY);
        }
        else {
          tv.setTextColor(Color.BLACK);
        }
        return view;
      }
    };
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sItems.setAdapter(adapter);
    sItems.setOnItemSelectedListener(new OnItemSelectedListener(){
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1,
          int position, long id) {
        if(position == 0)
          emailButton.setClickable(false);
        else
          emailButton.setClickable(true);
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }});

    emailButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        composeEmail(emailText.getText().toString(), sItems.getSelectedItem().toString(), null);
      }
    });

    return root;
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);

  }
  //Intent Way to send Email
  public void composeEmail(String text, String subject, Uri attachment) {
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", "abc@gmail.com", null));
    if (subject == "General"){
      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "General Feedback");
    }
    else {
      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for "+ subject);
    }
    emailIntent.putExtra(Intent.EXTRA_TEXT, text);
    startActivity(Intent.createChooser(emailIntent, "Send email..."));
  }


}
