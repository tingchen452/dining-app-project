package ui.menuDynamic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nwtft.dinningapp.R;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.AdapterVH> implements Filterable {
    private static final String TAG= "MAdapter";
    List<SortedMeal> meals;
    List<SortedMeal> mealsList;

    public  DynamicAdapter (List<SortedMeal> list){
        meals=list;
        mealsList=new ArrayList<>(meals);
    }

    @NonNull
    @Override
    public AdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new AdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVH holder, int position) {
        SortedMeal m = meals.get(position);
        holder.title.setText(m.getTitle());
        holder.allergy.setText("Tags: "+m.displayAllergy());
        holder.stat.setText("Station: "+m.getLocation());
        boolean isEx = meals.get(position).isEx();
        holder.chld.setVisibility(isEx ? View.VISIBLE : View.GONE);

    }



    @Override
    public int getItemCount() {
        return meals.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SortedMeal> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(mealsList);
            }
            else{
                String filterPattern=constraint.toString().toLowerCase().trim();
                for(SortedMeal item:mealsList){
                    if(item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meals.clear();
            meals.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };



    class AdapterVH extends RecyclerView.ViewHolder{
        private static final String TAG = "VH";
        TextView title, allergy, stat;
        ConstraintLayout chld;

        public AdapterVH(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.Title);
            allergy= itemView.findViewById(R.id.Allergies);
            stat = itemView.findViewById(R.id.Stations);
            chld=itemView.findViewById(R.id.chdCS);

            title.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    SortedMeal meal = meals.get(getAdapterPosition());
                    meal.setEx(!meal.isEx());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
