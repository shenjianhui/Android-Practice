package es.source.code.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.AllDish;
import es.source.code.model.User;
import es.source.code.tool.AllDishAdapter;
import es.source.code.tool.MyPagerAdapter;

public class FoodView extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<AllDish> coldDishList = new ArrayList<>();
    private List<AllDish> hotDishList = new ArrayList<>();
    private List<AllDish> seaFoodList = new ArrayList<>();
    private List<AllDish> drinkList = new ArrayList<>();
    private ViewPager pager;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;
    private List<String> titles = new ArrayList<String>();
    private PagerTabStrip pagerTitle;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user_info");

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setOnPageChangeListener(this);
        titles.add("冷菜");
        titles.add("热菜");
        titles.add("海鲜");
        titles.add("酒水");
        inflater = LayoutInflater.from(this);

        View view1 = inflater.inflate(R.layout.recyclerview, null);
        View view2 = inflater.inflate(R.layout.recyclerview, null);
        View view3 = inflater.inflate(R.layout.recyclerview, null);
        View view4 = inflater.inflate(R.layout.recyclerview, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        viewAdapter = new MyPagerAdapter(viewList,titles);
        pager.setAdapter(viewAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        initColdDishes();
        initHotDishes();
        initSeaFoods();
        initDrinks();
        RecyclerView recyclerView1 = view1.findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = view2.findViewById(R.id.recyclerView);
        RecyclerView recyclerView3 = view3.findViewById(R.id.recyclerView);
        RecyclerView recyclerView4 = view4.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        recyclerView4.setLayoutManager(linearLayoutManager4);
        AllDishAdapter adapter1 = new AllDishAdapter(coldDishList);
        AllDishAdapter adapter2 = new AllDishAdapter(hotDishList);
        AllDishAdapter adapter3 = new AllDishAdapter(seaFoodList);
        AllDishAdapter adapter4 = new AllDishAdapter(drinkList);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        recyclerView4.setAdapter(adapter4);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) //得到被点击的item的itemId
        {
            case  R.id.id_item1 :  //对应的ID就是在add方法中所设定的Id
                //Toast.makeText(this, "已点菜品", Toast.LENGTH_SHORT).show();
                String data = "No";
                Intent intent = new Intent(this,FoodOrderView.class);
                intent.putExtra("user_info", user);
                intent.putExtra("YesOrNo", data);
                startActivity(intent);
                break;
            case  R.id.id_item2:
                String data1 = "Yes";
                Intent intent1 = new Intent(this,FoodOrderView.class);
                intent1.putExtra("user_info", user);
                intent1.putExtra("YesOrNo", data1);
                startActivity(intent1);
                break;
            case  R.id.id_item3:
                Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //当滚动状态改变时被调用
    public void onPageScrollStateChanged(int arg0) {

    }

    //滚动时调用
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    //当页卡被选中时调用
    public void onPageSelected(int arg0) {
        //Toast.makeText(this, "这是第"+(arg0+1)+"个界面", Toast.LENGTH_SHORT).show();
    }

    private void initColdDishes(){
        for(int i = 0;i < 5; i++){
            AllDish dish1 = new AllDish(1,"鱼卵沙拉冷盘",R.drawable.colddish1,"18¥");
            coldDishList.add(dish1);
            AllDish dish2 = new AllDish(2,"卤牛腱冷盘",R.drawable.colddish2,"28¥");
            coldDishList.add(dish2);
        }
    }

    private void initHotDishes(){
        for(int i = 0;i < 5; i++){
            AllDish dish1 = new AllDish(3,"糖醋排骨",R.drawable.hotdish1,"36¥");
            hotDishList.add(dish1);
            AllDish dish2 = new AllDish(4,"可乐鸡翅",R.drawable.hotdish2,"28¥");
            hotDishList.add(dish2);
        }
    }

    private void initSeaFoods(){
        for(int i = 0;i < 5; i++){
            AllDish dish1 = new AllDish(5,"什锦海鲜面疙瘩",R.drawable.seafood1,"16¥");
            seaFoodList.add(dish1);
            AllDish dish2 = new AllDish(6,"海鲜煎饼",R.drawable.seafood2,"12¥");
            seaFoodList.add(dish2);
        }
    }

    private void initDrinks(){
        for(int i = 0;i < 5; i++){
            AllDish dish1 = new AllDish(7,"野莓奶昔",R.drawable.drink1,"10¥");
            drinkList.add(dish1);
            AllDish dish2 = new AllDish(8,"玫瑰情人露",R.drawable.drink2,"14¥");
            drinkList.add(dish2);
        }
    }
}
