package es.source.code.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.content.Intent;

public class SCOSEntry extends AppCompatActivity{

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
        }

        if ((x1 - x2 > 50)&&(y1 - y2 < 50)&&(y2 - y1 < 50)&&(x2 - x1 < 50)) {
            //Toast.makeText(SCOSEntry.this, "向左滑", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent("scos.intent.action.SCOSMAIN");
            //intent.addCategory("scos.intent.category.SCOSLAUNCHER");
            String data = "FromEntry";
            Intent intent = new Intent(this,MainScreen.class);
            intent.putExtra("extra_data", data);
            startActivity(intent);
        }
        return super.onTouchEvent(event);
    }

}
