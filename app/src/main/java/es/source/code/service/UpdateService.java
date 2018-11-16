package es.source.code.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.br.NotificationBR;
import es.source.code.tool.DBOpenHelper;
import es.source.code.tool.HttpUtilsHttpURLConnection;
import es.source.code.tool.MyApplication;
import static es.source.code.tool.MyApplication.getContext;

public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateService");
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle data = msg.getData();
                String key = data.getString("result");
                if(!key.equals("")) {
//                    try {
//                        JSONObject json = new JSONObject(key);
//                        String name = (String) json.get("name");
//                        int price = (int) json.get("price");
//                        String style = (String) json.get("style");
//                        int inventory = (int) json.get("inventory");
                    try {
                        Document doc;
                        String name;
                        int price;
                        String style;
                        int inventory;
                        doc = DocumentHelper.parseText(key);
                        Element rootElt = doc.getRootElement();
                        name = rootElt.elementTextTrim("name");
                        price = Integer.parseInt(rootElt.elementTextTrim("price"));
                        style = rootElt.elementTextTrim("style");
                        inventory = Integer.parseInt(rootElt.elementTextTrim("inventory"));

                        byte[] img = PicToByte(R.drawable.colddish3);
                        DBOpenHelper helper = new DBOpenHelper(getContext(),"Dish.db");
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor c = db.rawQuery("select * from dish where dishName=?",new String[]{name});
                        if(c.getCount()==0) {
                            ContentValues values = new ContentValues();
                            values.put("_id",9);
                            values.put("dishName",name);
                            values.put("dishImage",img);
                            values.put("dishPrice",price);
                            values.put("inventory",inventory);
                            values.put("style",style);
                            db.insert("dish", null, values);
                        }
                        db.close();

                        Notification notification = new Notification();
                        notification.when = System.currentTimeMillis();
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        notification.tickerText = "New message";
                        notification.icon = R.mipmap.ic_launcher_round;
                        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mysound );
                        Intent resultIntent = new Intent(UpdateService.this, MainScreen.class);
                        notification.contentIntent = PendingIntent.getActivity(UpdateService.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        Intent cancelIntent = new Intent(UpdateService.this,NotificationBR.class);
                        cancelIntent.setAction("notification_cancelled");
                        cancelIntent.putExtra("id", 6);
                        PendingIntent PICancel = PendingIntent.getBroadcast(UpdateService.this,0,cancelIntent,0);
                        notification.deleteIntent = PICancel;
                        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
                        remoteViews.setImageViewResource(R.id.noti_image, R.mipmap.ic_launcher);
                        remoteViews.setTextViewText(R.id.noti_title, "新品上架：");
                        remoteViews.setTextViewText(R.id.noti_content1, "菜名："+name);
                        remoteViews.setTextViewText(R.id.noti_content2, "价格：" + price + "¥，数量：" + inventory+ "份");
                        remoteViews.setTextViewText(R.id.noti_time, new StringBuilder().append(Calendar.getInstance().get(Calendar.YEAR)).append("-")
                                .append(Calendar.getInstance().get(Calendar.MONTH)).append("-")
                                .append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).append("  ").append(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)).append(":")
                                .append(Calendar.getInstance().get(Calendar.MINUTE)).append(":")
                                .append(Calendar.getInstance().get(Calendar.SECOND)));
                        remoteViews.setOnClickPendingIntent(R.id.noti_clear,PICancel);
                        notification.contentView = remoteViews;
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(6, notification);

                    }catch (DocumentException e) {
                        e.printStackTrace();
                    }

//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    };

    @Override
    protected void onHandleIntent(Intent intent) {

//        String name = "东北家拌凉菜";
//        byte[]img = PicToByte(R.drawable.colddish3);
//        int price = 16;
//        int inventory = 15;
//        String style = "冷菜";



        new Thread(new Runnable() {
            @Override
            public void run() {
                String url=HttpUtilsHttpURLConnection.BASE_URL+"/FoodUpdateService";
                Map<String, String> params = new HashMap<String, String>();
                String isUpdate = "yes";
                params.put("isUpdate",isUpdate);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url,params);
                Message msg = new Message();
                msg.what=0x11;
                Bundle data=new Bundle();
                data.putString("result",result);
                msg.setData(data);
                handler.sendMessage(msg);
            }

        }).start();

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher_round);
//        builder.setContentTitle("新品上架：");
//        builder.setContentText(name+"，"+price+"，"+style);
//        builder.setTicker("New message");//第一次提示消息的时候显示在通知栏上
//        builder.setNumber(1);
//        builder.setLargeIcon(btm);
//        builder.setAutoCancel(true);
//
//        DBOpenHelper helper = new DBOpenHelper(getContext(),"Dish.db");
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor c = db.rawQuery("select * from dish where dishName=?",new String[]{name});
//        if(c.getCount()==0) {
//            ContentValues values = new ContentValues();
//            values.put("_id",9);
//            values.put("dishName",name);
//            values.put("dishImage",img);
//            values.put("dishPrice",price);
//            values.put("inventory",inventory);
//            values.put("style",style);
//            db.insert("dish", null, values);
//        }
//        db.close();
//
//        Intent resultIntent = new Intent(this, FoodDetailed.class);
//        resultIntent.putExtra("dishName",name);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);
//        notificationManager.notify(0, builder.build());

    }


    private byte[] PicToByte(int resourceID)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = MyApplication.getContext().getResources().openRawResource(resourceID);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        //压缩图片，100代表不压缩（0～100）
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
