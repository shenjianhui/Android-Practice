package es.source.code.service;

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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.tool.DBOpenHelper;
import es.source.code.tool.MyApplication;
import static es.source.code.tool.MyApplication.getContext;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "es.source.code.service.action.FOO";
    private static final String ACTION_BAZ = "es.source.code.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "es.source.code.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "es.source.code.service.extra.PARAM2";

    public UpdateService() {
        super("UpdateService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String name = "东北家拌凉菜";
        byte[]img = PicToByte(R.drawable.colddish3);
        int price = 16;
        int inventory = 15;
        String style = "冷菜";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("新品上架：");
        builder.setContentText(name+"，"+price+"，"+style);
        builder.setTicker("New message");//第一次提示消息的时候显示在通知栏上
        builder.setNumber(1);
        builder.setLargeIcon(btm);
        builder.setAutoCancel(true);

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

        Intent resultIntent = new Intent(this, FoodDetailed.class);
        resultIntent.putExtra("dishName",name);
        //resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
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
