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
    public List<AllDish> myAllDishList;
    private SubClickListener subClickListener;
    public AllDish d;

    public void setsubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }

    public interface SubClickListener {
        void OntopicClickListener(View v, AllDish d, int position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        View allDishView;
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        TextView inventory;
        Button dishSelect;

        public ViewHolder(View view){
            super(view);
            allDishView = view;
            dishImage = view.findViewById(R.id.alldish_image);
            dishName = view.findViewById(R.id.alldish_name);
            dishPrice = view.findViewById(R.id.alldish_price);
            dishSelect = view.findViewById(R.id.alldish_select);
            inventory = view.findViewById(R.id.alldish_inventory);

        }
    }

    public AllDishAdapter(List<AllDish> allDishList,AllDish dish){
        myAllDishList = allDishList;
        d = dish;
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
                    d = new AllDish(allDish.getDishId(),allDish.getDishName(),allDish.getDishPrice());
                    d.setNumber(1);
                    if (subClickListener != null) {
                        subClickListener.OntopicClickListener(view, d, position);
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AllDishAdapter.ViewHolder holder, int position) {
        AllDish allDish = myAllDishList.get(position);
        holder.dishImage.setImageBitmap(allDish.getDishImg());
        holder.dishName.setText(allDish.getDishName());
        holder.inventory.setText("库存:"+allDish.getInventory());
        holder.dishPrice.setText(allDish.getDishPrice()+"¥");
        holder.dishSelect.setText("点菜");
    }

    @Override
    public int getItemCount() {
        return myAllDishList.size();
    }
}
