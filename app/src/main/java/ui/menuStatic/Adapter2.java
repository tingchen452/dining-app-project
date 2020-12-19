package ui.menuStatic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import nwtft.dinningapp.R;
import ui.menuDynamic.SortedMeal;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.AdapterVH> {

  private static final String TAG = "MAdapter";
  List<SortedMeal> meals;

  public Adapter2(ArrayList<SortedMeal> m) {
    meals = m;
  }

  @NonNull
  @Override
  public AdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    return new AdapterVH(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterVH holder, int position) {
    SortedMeal m = meals.get(position);
    holder.title.setText(m.getTitle());
    //holder.allergy.setText(m.displayAllergy());
    boolean isEx = meals.get(position).isEx();
    holder.chld.setVisibility(isEx ? View.VISIBLE : View.GONE);

  }


  @Override
  public int getItemCount() {
    return meals.size();
  }

  class AdapterVH extends RecyclerView.ViewHolder {

    private static final String TAG = "VH";
    TextView title, allergy;
    ConstraintLayout chld;

    public AdapterVH(@NonNull View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.Title);
      allergy = itemView.findViewById(R.id.Allergies);
      chld = itemView.findViewById(R.id.chdCS);

/*      title.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          SortedMeal meal = meals.get(getAdapterPosition());
          meal.setEx(!meal.isEx());
          notifyItemChanged(getAdapterPosition());
        }
      }); */
    }
  }
}





