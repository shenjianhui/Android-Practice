package es.source.code.tool;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.model.AllDish;

public class AllDishAdapter extends RecyclerView.Adapter<AllDishAdapter.ViewHolder> {
    private List<AllDish> myAllDishList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View allDishView;
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        Button dishSelect;

        public ViewHolder(View view){
            super(view);
            allDishView = view;
            dishImage = view.findViewById(R.id.alldish_image);
            dishName = view.findViewById(R.id.alldish_name);
            dishPrice = view.findViewById(R.id.alldish_price);
            dishSelect = view.findViewById(R.id.alldish_select);
        }
    }

    public AllDishAdapter(List<AllDish> allDishList){
        myAllDishList = allDishList;
    }

    @Override
    public AllDishAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alldish_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.allDishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                AllDish allDish = myAllDishList.get(position);
                //Toast.makeText(view.getContext(),"you clicked at "+allDish.getDishName(),Toast.LENGTH_SHORT).show();
                int dishId = allDish.getDishId();
                Intent intent = new Intent(view.getContext(),FoodDetailed.class);
                intent.putExtra("dish_id", dishId);
                view.getContext().startActivity(intent);
            }
        });
        holder.dishSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.dishSelect.getText().equals("点菜")) {
                    int position = holder.getAdapterPosition();
                    AllDish allDish = myAllDishList.get(position);
                    Toast.makeText(view.getContext(), allDish.getDishName() + ",点菜成功！", Toast.LENGTH_SHORT).show();
                    holder.dishSelect.setText("退点");
                }else if(holder.dishSelect.getText().equals("退点")){
                    int position = holder.getAdapterPosition();
                    AllDish allDish = myAllDishList.get(position);
                    Toast.makeText(view.getContext(),allDish.getDishName()+",退点成功！",Toast.LENGTH_SHORT).show();
                    holder.dishSelect.setText("点菜");
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AllDishAdapter.ViewHolder holder, int position) {
        AllDish allDish = myAllDishList.get(position);
        holder.dishImage.setImageResource(allDish.getDishImageId());
        holder.dishName.setText(allDish.getDishName());
        holder.dishPrice.setText(allDish.getDishPrice());
        holder.dishSelect.setText("点菜");
    }

    @Override
    public int getItemCount() {
        return myAllDishList.size();
    }
}
