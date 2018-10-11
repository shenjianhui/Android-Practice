package es.source.code.tool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import es.source.code.activity.R;
import es.source.code.model.AllDish;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<AllDish> myDetailDishList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View DetailDishView;
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        EditText dishRemark;
        Button dishSelect;

        public ViewHolder(View view){
            super(view);
            DetailDishView = view;
            dishImage = view.findViewById(R.id.detail_image);
            dishName = view.findViewById(R.id.detail_name);
            dishPrice = view.findViewById(R.id.detail_price);
            dishRemark = view.findViewById(R.id.detail_addremark);
            dishSelect = view.findViewById(R.id.detail_select);
        }
    }

    public DetailAdapter(List<AllDish> detailDishList){
        myDetailDishList = detailDishList;
    }

    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.dishSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.dishSelect.getText().equals("点菜")){
                    int position = holder.getAdapterPosition();
                    AllDish detailDish = myDetailDishList.get(position);
                    Toast.makeText(view.getContext(),detailDish.getDishName()+",点菜成功！",Toast.LENGTH_SHORT).show();
                    holder.dishSelect.setText("退点");
                }else if(holder.dishSelect.getText().equals("退点")){
                    int position = holder.getAdapterPosition();
                    AllDish detailDish = myDetailDishList.get(position);
                    Toast.makeText(view.getContext(),detailDish.getDishName()+",退点成功！",Toast.LENGTH_SHORT).show();
                    holder.dishSelect.setText("点菜");
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        AllDish detailDish = myDetailDishList.get(position);
        holder.dishImage.setImageResource(detailDish.getDishImageId());
        holder.dishName.setText(detailDish.getDishName());
        holder.dishPrice.setText(detailDish.getDishPrice());
        //holder.dishPrice.setText("");
        holder.dishSelect.setText("点菜");
    }

    @Override
    public int getItemCount() {
        return myDetailDishList.size();
    }



}
