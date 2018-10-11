package es.source.code.activity;

import android.content.Intent;
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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.Order;
import es.source.code.model.User;
import es.source.code.tool.MyPagerAdapter;
import es.source.code.tool.OrderNoAdapter;
import es.source.code.tool.OrderYesAdapter;

public class FoodOrderView extends AppCompatActivity {
    private List<Order> orderNoList = new ArrayList<>();
    private List<Order> orderYesList = new ArrayList<>();
    private ViewPager pager;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;
    private List<String> titles = new ArrayList<String>();
    private PagerTabStrip pagerTitle;
    private OrderNoAdapter adapter1;
    private OrderYesAdapter adapter2;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user_info");
        String data = intent.getStringExtra("YesOrNo");
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
        if(data.equals("Yes")){
            pager.setCurrentItem(1);
        }else if(data.equals("No")){
            pager.setCurrentItem(0);
        }
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        initOrderNo();
        initOrderYes();
        RecyclerView recyclerView1 = view1.findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = view2.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        adapter1 = new OrderNoAdapter(orderNoList);
        adapter2 = new OrderYesAdapter(orderYesList);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        setFooterView1(recyclerView1);
        setFooterView2(recyclerView2);

    }

    private void setFooterView1(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.foot_view, view, false);
        adapter1.setFooterView(footer);
    }

    private void setFooterView2(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.foot_view, view, false);
        adapter2.setFooterView(footer);
        Button Submit = footer.findViewById(R.id.order_submit);
        Submit.setText("结账");
        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(user!=null&&user.getOldUser()) {
                    Toast.makeText(view.getContext(), "您好，老顾客，本次你可享受7折优惠", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initOrderNo(){
        for(int i = 0;i < 3; i++){
            Order orderNo1 = new Order("糖醋排骨","36¥","1份","少糖多醋");
            orderNoList.add(orderNo1);
            Order orderNo2 = new Order("可乐鸡翅","28¥","2份","少放酱油");
            orderNoList.add(orderNo2);
        }
    }

    private void initOrderYes(){
        for(int i = 0;i < 3; i++){
            Order orderYes1 = new Order("什锦海鲜面疙瘩","16¥","3份","少放点调理");
            orderYesList.add(orderYes1);
            Order orderYes2 = new Order("海鲜煎饼","12¥","2份","多放点葱");
            orderYesList.add(orderYes2);
        }
    }
}
