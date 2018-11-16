package es.source.code.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.AllDish;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;
import es.source.code.service.UpdateService;
import es.source.code.tool.AllDishAdapter;
import es.source.code.tool.DBOpenHelper;
import es.source.code.tool.MyPagerAdapter;
import static es.source.code.tool.MyApplication.getContext;

public class FoodView extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<AllDish> coldDishList = new ArrayList<>();
    private List<AllDish> hotDishList = new ArrayList<>();
    private List<AllDish> seaFoodList = new ArrayList<>();
    private List<AllDish> drinkList = new ArrayList<>();
    public ViewPager pager;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;
    private List<String> titles = new ArrayList<String>();
    private PagerTabStrip pagerTitle;
    User user = new User();
    AllDish d = new AllDish();
    public List<AllDish> orderDishList = new ArrayList<>();
    int FLAG;
    int MenuFlag;

    @SuppressLint("HandlerLeak")
    private Handler sMessageHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 10:
                    //更新菜品
                    //Log.i("info", "进行菜品更新----"+ msg.getData().getString("name") +"---"+msg.getData().getInt("inventory"));
                    String name1 = msg.getData().getString("name1");
                    int inventory1 = msg.getData().getInt("inventory1");
                    int price1 = msg.getData().getInt("price1");
                    String name2 = msg.getData().getString("name2");
                    int inventory2 = msg.getData().getInt("inventory2");
                    int price2 = msg.getData().getInt("price2");
                    DBOpenHelper helper = new DBOpenHelper(getContext(),"Dish.db");
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update dish set inventory="+inventory1+",dishPrice="+price1+" where dishName=?",new String[]{name1});
                    db.execSQL("update dish set inventory="+inventory2+",dishPrice="+price2+" where dishName=?",new String[]{name2});
                    db.close();
                    SharedPreferences sharedPreferences = getSharedPreferences("menuFlag",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("flag",0);
                    editor.apply();
                    refresh();
                    break;
                case 0:

                    break;
                default:
                    break;
            }
            unbindService(connection);
        }
    };

    public Messenger mMessenger;
    public Message mMessage;
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMessenger = new Messenger(service);
            if(FLAG == 1) {
                mMessage = Message.obtain(null, 1);
            }else if(FLAG == 0){
                mMessage = Message.obtain(null, 0);
            }
            Bundle mBundle=new Bundle();
            mBundle.putString("msg", "客户端--》服务端");
            mMessage.setData(mBundle);
            mMessage.replyTo=new Messenger(sMessageHandler);
            try {
                mMessenger.send(mMessage);
            }catch (RemoteException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        SharedPreferences sharedPreferences = getSharedPreferences("menuFlag",Context.MODE_PRIVATE);
        MenuFlag = sharedPreferences.getInt("flag",1);

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

        initDishes();

        RecyclerView recyclerView1 = view1.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        AllDishAdapter adapter1 = new AllDishAdapter(coldDishList,d);
        recyclerView1.setAdapter(adapter1);

        RecyclerView recyclerView2 = view2.findViewById(R.id.recyclerView);
        RecyclerView recyclerView3 = view3.findViewById(R.id.recyclerView);
        RecyclerView recyclerView4 = view4.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);

        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        recyclerView4.setLayoutManager(linearLayoutManager4);

        AllDishAdapter adapter2 = new AllDishAdapter(hotDishList,d);
        AllDishAdapter adapter3 = new AllDishAdapter(seaFoodList,d);
        AllDishAdapter adapter4 = new AllDishAdapter(drinkList,d);

        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        recyclerView4.setAdapter(adapter4);

        adapter1.setsubClickListener(new AllDishAdapter.SubClickListener() {
            public void OntopicClickListener(View v,AllDish dish, int position) {
                d = dish;
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
        adapter2.setsubClickListener(new AllDishAdapter.SubClickListener() {
            public void OntopicClickListener(View v,AllDish dish, int position) {
                d = dish;
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
        adapter3.setsubClickListener(new AllDishAdapter.SubClickListener() {
            public void OntopicClickListener(View v,AllDish dish, int position) {
                d = dish;
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
        adapter4.setsubClickListener(new AllDishAdapter.SubClickListener() {
            public void OntopicClickListener(View v,AllDish dish, int position) {
                d = dish;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        if(MenuFlag == 1) {
            getMenuInflater().inflate(R.menu.menu, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu1, menu);
        }
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
            case  R.id.id_item4:
                if(item.getTitle().equals("启动实时更新")) {
                    FLAG = 1;
                    //item.setTitle("停止实时更新");
                    Intent bindIntent = new Intent(this, ServerObserverService.class);
                    bindService(bindIntent, connection, BIND_AUTO_CREATE);

                } else if(item.getTitle().equals("停止实时更新")) {
                    FLAG = 0;
                    Intent bindIntent = new Intent(this, ServerObserverService.class);
                    bindService(bindIntent, connection, BIND_AUTO_CREATE);
                    //item.setTitle("启动实时更新");
                    SharedPreferences sharedPreferences = getSharedPreferences("menuFlag",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("flag");
                    editor.apply();
                    Toast.makeText(this,"已停止实时更新",Toast.LENGTH_SHORT).show();
                    item.setTitle("启动实时更新");
                    //unbindService(connection);
                }
                break;
            case R.id.id_item5:
                Intent service = new Intent(getContext(), UpdateService.class);
                getContext().startService(service);
                break;
            case R.id.id_item6:
                DBOpenHelper helper = new DBOpenHelper(this,"Dish.db");
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("dish","dishName = ?",new String[]{"东北家拌凉菜"});
                ContentValues values = new ContentValues();
                values.put("inventory",10);
                values.put("dishPrice",18);
                db.update("dish",values,"dishName = ?",new String[]{"鱼卵沙拉冷盘"});
                values.clear();
                values.put("inventory",20);
                values.put("dishPrice",28);
                db.update("dish",values,"dishName = ?",new String[]{"卤牛腱冷盘"});
                db.close();
                Toast.makeText(this,"重置成功！",Toast.LENGTH_SHORT).show();
                refresh();
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

    private void initDishes(){
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
                int inventory = c.getInt(c.getColumnIndex("inventory"));
                String style = c.getString(c.getColumnIndex("style"));
                AllDish myDish = new AllDish(dishId,dishName, dishImage,inventory ,dishPrice);
                if(style.equals("冷菜")){
                    coldDishList.add(myDish);
                }else if(style.equals("热菜")){
                    hotDishList.add(myDish);
                }else if(style.equals("海鲜")){
                    seaFoodList.add(myDish);
                }else if(style.equals("酒水")){
                    drinkList.add(myDish);
                }

            }
            c.close();
        }
        db.close();
    }


    private void refresh(){
        finish();
        Intent intent = new Intent(FoodView.this, FoodView.class);
        intent.putExtra("user_info", user);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(connection);
        SharedPreferences sharedPreferences = getSharedPreferences("menuFlag",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("flag");
        editor.apply();
    }

}
