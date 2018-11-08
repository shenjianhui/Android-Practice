package es.source.code.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import es.source.code.service.UpdateService;
import es.source.code.tool.DBOpenHelper;
import static es.source.code.tool.MyApplication.getContext;

public class Test extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button button1 = findViewById(R.id.add);
        Button button2 = findViewById(R.id.delete);
        Button button3 = findViewById(R.id.reset);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add:
                Intent service = new Intent(getContext(), UpdateService.class);
                getContext().startService(service);
                break;
            case R.id.delete:
                DBOpenHelper helper = new DBOpenHelper(this,"Dish.db");
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("dish","dishName = ?",new String[]{"东北家拌凉菜"});
                db.close();
                Toast.makeText(this,"删除成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reset:
                DBOpenHelper helper1 = new DBOpenHelper(this,"Dish.db");
                SQLiteDatabase db1 = helper1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("inventory",10);
                db1.update("dish",values,"dishName = ?",new String[]{"鱼卵沙拉冷盘"});
                values.clear();
                values.put("inventory",20);
                db1.update("dish",values,"dishName = ?",new String[]{"卤牛腱冷盘"});
                db1.close();
                Toast.makeText(this,"重置成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
