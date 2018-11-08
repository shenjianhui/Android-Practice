package es.source.code.tool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import es.source.code.activity.R;
import es.source.code.model.AllDish;

public class OrderYesAdapter extends RecyclerView.Adapter<OrderYesAdapter.ViewHolder> {
    private static List<AllDish> myAllDishList;
    private static final int NORMAL_TYPE = 0;
    private static final int FOOT_TYPE = 1;
    private static SubClickListener subClickListener;
    private View myFooterView;
    public void setsubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }
    public interface SubClickListener {
        void OntopicClickListener(View v, int price);
    }

    public OrderYesAdapter(List<AllDish> AllDishList){
        myAllDishList = AllDishList;
    }

    public View getFooterView() {
        return myFooterView;
    }
    public void setFooterView(View footerView) {
        myFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View orderView;
        TextView orderName;
        TextView orderPrice;
        TextView orderNumber;
        TextView orderRemark;
        TextView totalNumber;
        TextView totalPrice;
        Button Submit;

        public ViewHolder(View view, int viewType){
            super(view);
            if (viewType == FOOT_TYPE){
                totalNumber = view.findViewById(R.id.order_number);
                totalNumber.setText("菜品总数："+myAllDishList.size());
                totalPrice = view.findViewById(R.id.order_price);
                if(myAllDishList.size()==0){
                    totalPrice.setText("订单总价：0");
                }else {
                    int Price = 0;
                    for(int i=0;i<myAllDishList.size();i++) {
                        Price +=  myAllDishList.get(i).getDishPrice()*myAllDishList.get(i).getNumber();
                        totalPrice.setText("订单总价：" + Price +"¥");
                        if (subClickListener != null) {
                            subClickListener.OntopicClickListener(view, Price);
                        }
                    }
                }
                Submit = view.findViewById(R.id.order_submit);
                return;
            }
            orderView = view;
            orderName = view.findViewById(R.id.order_yes_name);
            orderPrice = view.findViewById(R.id.order_yes_price);
            orderNumber = view.findViewById(R.id.order_yes_number);
            orderRemark = view.findViewById(R.id.order_yes_remark);

        }
    }


    @Override
    public OrderYesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (myFooterView != null && viewType == FOOT_TYPE) {
            return new ViewHolder(myFooterView,FOOT_TYPE);
        }
        View normal_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_yes,parent,false);
        return new ViewHolder(normal_view,NORMAL_TYPE);
    }

    @Override
    public void onBindViewHolder(OrderYesAdapter.ViewHolder holder, int position) {
        if ( getItemViewType(position) == NORMAL_TYPE) {
            AllDish allDish = myAllDishList.get(position);
            holder.orderName.setText(allDish.getDishName());
            holder.orderPrice.setText(allDish.getDishPrice()+"¥");
            holder.orderNumber.setText(allDish.getNumber()+"份");
            if(allDish.getDishRemark()==null){
                holder.orderRemark.setText("备注：无");
            }else {
                holder.orderRemark.setText("备注：" + allDish.getDishRemark());
            }
            return;
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(myFooterView == null){
            return myAllDishList.size();
        }else{
            return myAllDishList.size()+1;
        }
    }

    public int getItemViewType(int position) {
        if ( myFooterView == null){
            return NORMAL_TYPE;
        }

        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

}
