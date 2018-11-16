package es.source.code.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import es.source.code.tool.EmailSender;
import es.source.code.tool.MyApplication;

public class SCOSHelper extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public GridView gridView;
    public List<Map<String,Object>> dataList;
    public SimpleAdapter adapter;
    public final static int SEND_SUCCESS = 1;
    List<Integer> icon = Arrays.asList(R.drawable.ic_helprule,R.drawable.ic_helpabout,
            R.drawable.ic_helptel,R.drawable.ic_helpnote,R.drawable.ic_helpmail);
    List<String> iconName = Arrays.asList("使用协议","关于系统","电话帮助","短信帮助","邮件帮助");
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(MyApplication.getContext(),"求助邮件已发送成功",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoshelper);

        gridView = findViewById(R.id.gridView_help);
        dataList = new ArrayList<>();
        for (int i = 0; i < icon.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("ItemImage", icon.get(i));
            map.put("ItemText", iconName.get(i));
            dataList.add(map);
        }
        String[] from = {"ItemImage", "ItemText"};
        int[] to = {R.id.image_help, R.id.text_help};
        adapter = new SimpleAdapter(this, dataList, R.layout.help_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            Toast.makeText(this,"我是"+iconName.get(i)+"被点击了",Toast.LENGTH_SHORT).show();
        }
        if (i == 1) {
            Toast.makeText(this,"我是"+iconName.get(i)+"被点击了",Toast.LENGTH_SHORT).show();
        }
        if (i == 2) {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.CALL_PHONE },1);
            }else{
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:5554"));
                    startActivity(intent);
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        }
        if (i == 3) {
            String tel = "5554";
            String content = "test SCOS helper";
            SmsManager smsManager = SmsManager.getDefault();
            try {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                                        new Intent(this,SCOSHelper.class), 0);
                smsManager.sendTextMessage(tel, null, content, pendingIntent, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"求助短信发送成功",Toast.LENGTH_SHORT).show();
        }
        if (i == 4) {
            new Thread(new Runnable() {
                public void run(){
                    try {
                        EmailSender sender = new EmailSender();
                        sender.setProperties("smtp.qq.com", "465");
                        sender.setMessage("870440650@qq.com", "发送邮件测试", "成功发送邮件！");
                        sender.setReceiver(new String[]{"870440650@qq.com"});
                        sender.sendEmail("smtp.qq.com", "870440650@qq.com", "nfxsfsgzewyabfdf");
//                        Intent data = new Intent(Intent.ACTION_SENDTO);
//                        data.setData(Uri.parse("mailto:shenjianhui0813@163.com"));
//                        data.putExtra(Intent.EXTRA_SUBJECT, "发送邮件测试");
//                        data.putExtra(Intent.EXTRA_TEXT, "成功发送邮件！");
//                        startActivity(data);
                        Message message = new Message();
                        message.what = SEND_SUCCESS;
                        handler.sendMessage(message);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
                    }catch (AddressException e){
                        e.printStackTrace();
                    }catch (MessagingException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
