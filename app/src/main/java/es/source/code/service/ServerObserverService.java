package es.source.code.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

public class ServerObserverService extends Service {
    public ServerObserverService() {
    }

    String name1,name2;
    int inventory1,inventory2;
    @SuppressLint("HandlerLeak")
    private Handler cMessageHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    //开启线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                name1 = "鱼卵沙拉冷盘";
                                inventory1 = 5;
                                name2 = "卤牛腱冷盘";
                                inventory2 = 18;
                                Thread.sleep(300);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    if(isUIProcess()) {
                        Log.i("info","启动服务-------"+msg.getData().get("msg"));
                        Messenger mMessenger = msg.replyTo;
                        Message mMessage = Message.obtain(null, 10);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("name1", name1);
                        mBundle.putInt("inventory1",inventory1);
                        mBundle.putString("name2", name2);
                        mBundle.putInt("inventory2",inventory2);
                        mMessage.setData(mBundle);
                        try {
                            mMessenger.send(mMessage);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0:
                    Log.i("info", "停止更新-------"+msg.getData().get("msg"));
                    //Thread.currentThread().stop();
                    Messenger mMessenger = msg.replyTo;
                    Message mMessage = Message.obtain(null, 0);
                    try {
                        mMessenger.send(mMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private final Messenger messenger = new Messenger(cMessageHandler);

    @Override
    public IBinder onBind(Intent intent) {

        return messenger.getBinder();
    }

    public boolean isUIProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public void onDestroy(){
        super.onDestroy();

    }

}
