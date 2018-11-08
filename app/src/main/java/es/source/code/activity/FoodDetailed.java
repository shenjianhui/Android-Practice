package es.source.code.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.AllDish;
import es.source.code.model.User;
import es.source.code.tool.DBOpenHelper;
import es.source.code.tool.DetailAdapter;

public class FoodDetailed extends AppCompatActivity{

    private List<AllDish> detailDishList = new ArrayList<>();
    public AllDish d = new AllDish();
    public List<AllDish> orderDishList = new ArrayList<>();
    User user = new User();
    int dishId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        initDetail();
        Intent intent = getIntent();
        dishId = intent.getIntExtra("dish_id",-1);
        user = (User) intent.getSerializableExtra("user_info");
        try {
            String name = intent.getStringExtra("dishName");
            if(!name.equals("")){
                DBOpenHelper helper = new DBOpenHelper(this,"Dish.db");
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from dish where dishName=?",new String[]{name});
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        dishId = id;
                    }
                }
                cursor.close();
                db.close();
            }
        }catch(Exception e){
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DetailAdapter adapter = new DetailAdapter(detailDishList);
        recyclerView.setAdapter(adapter);
        MoveToPosition(linearLayoutManager,recyclerView,dishId-1);
        adapter.setsubClickListener(new DetailAdapter.SubClickListener() {
            public void OntopicClickListener(View v, AllDish dish, int position) {
                d=dish;
                int Flag=0;
                for(int i=0;i<orderDishList.size();i++){
                    if(d.getDishId()==orderDishList.get(i).getDishId()){
                        Flag = 1;
                        orderDishList.get(i).setNumber(orderDishList.get(i).getNumber()+1);
                    }
                }
                if(Flag == 0) {
                    orderDishList.add(d);
                }
            }
        });
    }

    private void initDetail() {

        DBOpenHelper helper = new DBOpenHelper(this,"Dish.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from dish",null);
        if(c!=null){
            while (c.moveToNext()){
                int dishId = c.getInt(c.getColumnIndex("_id"));
                String dishName = c.getString(c.getColumnIndex("dishName"));
                byte[] pic = c.getBlob(c.getColumnIndex("dishImage"));
                Bitmap dishImage = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                int dishPrice = c.getInt(c.getColumnIndex("dishPrice"));
                AllDish detail = new AllDish(dishId,dishName, dishImage, dishPrice);
                detailDishList.add(detail);
            }
            c.close();
        }
        db.close();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
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
                intent.putExtra("orderDishList", (Serializable) orderDishList);
                startActivity(intent);
                break;
            case  R.id.id_item2:
                String data1 = "Yes";
                Intent intent1 = new Intent(this,FoodOrderView.class);
                intent1.putExtra("user_info", user);
                intent1.putExtra("YesOrNo", data1);
                intent1.putExtra("orderDishList", (Serializable) orderDishList);
                startActivity(intent1);
                break;
            case  R.id.id_item3:
                Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
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

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
}