package es.source.code.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.AllDish;
import es.source.code.model.User;
import es.source.code.tool.MyPagerAdapter;
import es.source.code.tool.OrderNoAdapter;
import es.source.code.tool.OrderYesAdapter;

public class FoodOrderView extends AppCompatActivity {
    private ProgressBar pb;
    private ViewPager pager;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;
    private List<String> titles = new ArrayList<String>();
    private PagerTabStrip pagerTitle;
    private OrderNoAdapter adapter1;
    private OrderYesAdapter adapter2;
    public AllDish d = new AllDish();
    public Button Submit;
    int Price;
    User user = new User();
    List<AllDish> orderDishList = new ArrayList<>();
    List<AllDish> tempList = new ArrayList<>();
    List<AllDish> tempDishList = new ArrayList<>();
    List<AllDish> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        pager = (ViewPager) findViewById(R.id.viewPager);
        //pager.setOnPageChangeListener(this);
        titles.add("未下订单");
        titles.add("已下订单");
        inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.recyclerview, null);
        View view2 = inflater.inflate(R.layout.recyclerview, null);
        viewList.add(view1);
        viewList.add(view2);
        viewAdapter = new MyPagerAdapter(viewList,titles);
        pager.setAdapter(viewAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        Intent intent = getIntent();
        try {
            user = (User) intent.getSerializableExtra("user_info");
        }catch(Exception e){
        }
        try {
            String data = intent.getStringExtra("YesOrNo");
            if (data.equals("Yes")) {
                pager.setCurrentItem(1);
            } else if (data.equals("No")) {
                pager.setCurrentItem(0);
            }
        }catch(Exception e){
        }
        try {
            tempDishList = (List<AllDish>) intent.getSerializableExtra("orderDishList");
            if (tempDishList.size() != 0) {
                orderDishList = tempDishList;
            }
        }catch(Exception e){
        }
        try {
            tempList = (List<AllDish>) intent.getSerializableExtra("orderList");
            if (tempList.size() != 0) {
                orderList = tempList;
            }
        }catch(Exception e){
        }

        RecyclerView recyclerView1 = view1.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        adapter1 = new OrderNoAdapter(orderDishList);
        recyclerView1.setAdapter(adapter1);
        setFooterView1(recyclerView1);
        adapter1.setsubClickListener(new OrderNoAdapter.SubClickListener() {
            @Override
            public void OntopicClickListener(View v, AllDish dish, int position) {
                d = dish;
                int i = orderDishList.indexOf(d);
                if(orderDishList.get(i).getNumber()<=1) {
                    orderDishList.remove(d);
                }else{
                    orderDishList.get(i).setNumber(orderDishList.get(i).getNumber()-1);
                }
                refresh();
            }
        });
        RecyclerView recyclerView2 = view2.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        adapter2 = new OrderYesAdapter(orderList);
        recyclerView2.setAdapter(adapter2);
        setFooterView2(recyclerView2);

        adapter2.setsubClickListener(new OrderYesAdapter.SubClickListener(){
            public void OntopicClickListener(View v,int price) {
                Price = price;
            }
        });
    }

    private void setFooterView1(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.foot_view, view, false);
        adapter1.setFooterView(footer);
        Button Submit = footer.findViewById(R.id.order_submit);
        Submit.setText("提交订单");
        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                orderList = orderDishList;
                Toast.makeText(view.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                refreshOrder();

            }
        });
        return;
    }

    private void setFooterView2(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.foot_view, view, false);
        adapter2.setFooterView(footer);
        pb = footer.findViewById(R.id.progressBar_pay);
        Submit = footer.findViewById(R.id.order_submit);
        Submit.setText("结账");
        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                if(user!=null&&user.getOldUser()) {
//                    Toast.makeText(view.getContext(), "您好，老顾客，本次你可享受7折优惠", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(view.getContext(), "暂时没有优惠", Toast.LENGTH_SHORT).show();
//                }
                new PayTask().execute(Submit);

            }
        });
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(FoodOrderView.this, FoodOrderView.class);
        intent.putExtra("orderDishList", (Serializable) orderDishList);
        intent.putExtra("user_info", user);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void refreshOrder(){
        finish();
        Intent intent = new Intent(FoodOrderView.this, FoodOrderView.class);
        intent.putExtra("orderList", (Serializable) orderList);
        intent.putExtra("user_info", user);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    class PayTask extends AsyncTask<Button, Integer, String> {
        protected void onPreExecute(){
            pb.setProgress(0);
            pb.setVisibility(View.VISIBLE);
        }
        protected String doInBackground(Button... params){
            for(int i=0;i<6;i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onProgressUpdate(Integer... values){
            pb.setProgress(values[0]);
        }
        protected void onPostExecute(String result){
            Toast.makeText(FoodOrderView.this,"本次结账金额："+Price+"元，获得积分："+Price, Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);
            Submit.setEnabled(false);
        }
    }

}
