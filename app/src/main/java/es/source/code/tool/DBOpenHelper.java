package es.source.code.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import es.source.code.activity.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static Integer Version = 1;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context,String name)
    {
        this(context, name,null, Version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists dish(_id integer primary key autoincrement,dishName text not null," +
                "dishImage blob not null,dishPrice integer not null,inventory integer not null,style text not null," +
                "remark text)");
        ContentValues values = new ContentValues();
        values.put("dishName","鱼卵沙拉冷盘");
        values.put("dishImage",PicToByte(R.drawable.colddish1));
        values.put("dishPrice",18);
        values.put("inventory",10);
        values.put("style","冷菜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","卤牛腱冷盘");
        values.put("dishImage",PicToByte(R.drawable.colddish2));
        values.put("dishPrice",28);
        values.put("inventory",20);
        values.put("style","冷菜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","糖醋排骨");
        values.put("dishImage",PicToByte(R.drawable.hotdish1));
        values.put("dishPrice",36);
        values.put("inventory",15);
        values.put("style","热菜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","可乐鸡翅");
        values.put("dishImage",PicToByte(R.drawable.hotdish2));
        values.put("dishPrice",28);
        values.put("inventory",15);
        values.put("style","热菜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","什锦海鲜面疙瘩");
        values.put("dishImage",PicToByte(R.drawable.seafood1));
        values.put("dishPrice",16);
        values.put("inventory",15);
        values.put("style","海鲜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","海鲜煎饼");
        values.put("dishImage",PicToByte(R.drawable.seafood2));
        values.put("dishPrice",12);
        values.put("inventory",15);
        values.put("style","海鲜");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","野莓奶昔");
        values.put("dishImage",PicToByte(R.drawable.drink1));
        values.put("dishPrice",10);
        values.put("inventory",15);
        values.put("style","酒水");
        db.insert("dish",null,values);
        values.clear();
        values.put("dishName","玫瑰情人露");
        values.put("dishImage",PicToByte(R.drawable.drink2));
        values.put("dishPrice",14);
        values.put("inventory",15);
        values.put("style","酒水");
        db.insert("dish",null,values);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private byte[] PicToByte(int resourceID)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = mContext.getResources().openRawResource(resourceID);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        //压缩图片，100代表不压缩（0～100）
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
