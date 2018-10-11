package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import es.source.code.model.AllDish;
import es.source.code.tool.DetailAdapter;

public class FoodDetailed extends AppCompatActivity{

    private List<AllDish> detailDishList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        initDetail();
        Intent intent = getIntent();
        int dishId = intent.getIntExtra("dish_id",-1);
       // Toast.makeText(this,dishId,Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DetailAdapter adapter = new DetailAdapter(detailDishList);
        recyclerView.setAdapter(adapter);
        MoveToPosition(linearLayoutManager,recyclerView,dishId-1);
    }

    private void initDetail() {

        AllDish detail1 = new AllDish(1,"鱼卵沙拉冷盘", R.drawable.colddish1, "18¥");
        detailDishList.add(detail1);
        AllDish detail2 = new AllDish(2,"卤牛腱冷盘",R.drawable.colddish2,"28¥");
        detailDishList.add(detail2);
        AllDish detail3 = new AllDish(3,"糖醋排骨", R.drawable.hotdish1, "36¥");
        detailDishList.add(detail3);
        AllDish detail4 = new AllDish(4,"可乐鸡翅",R.drawable.hotdish2,"28¥");
        detailDishList.add(detail4);
        AllDish detail5 = new AllDish(5,"什锦海鲜面疙瘩", R.drawable.seafood1, "16¥");
        detailDishList.add(detail5);
        AllDish detail6 = new AllDish(6,"海鲜煎饼",R.drawable.seafood2,"12¥");
        detailDishList.add(detail6);
        AllDish detail7 = new AllDish(7,"野莓奶昔", R.drawable.drink1, "10¥");
        detailDishList.add(detail7);
        AllDish detail8 = new AllDish(8,"玫瑰情人露",R.drawable.drink2,"14¥");
        detailDishList.add(detail8);

    }

    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

}