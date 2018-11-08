package es.source.code.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import es.source.code.model.User;
import es.source.code.tool.HttpUtilsHttpURLConnection;

public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_V = 1;
    public Button button;
    public Button button1;
    public Button button_reg;
    private ProgressBar progressBar;
    private EditText editTextName;
    private EditText editTextPwd;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_V:
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);

        editTextName = findViewById(R.id.editText_username);
        editTextPwd = findViewById(R.id.editText_pwd);
        button = findViewById(R.id.button_login);
        button_reg = findViewById( R.id.button_register);
        progressBar = findViewById(R.id.progressBar_login);
        button.setOnClickListener(this);
        button_reg.setOnClickListener(this);
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("userName","");
        if(name.equals("")){
            button.setVisibility(View.GONE);
        }else{
            button_reg.setVisibility(View.GONE);
            editTextName.setText(name);
        }
        button1 = findViewById(R.id.button_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("userName","");
                if(name.equals("")){
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.putInt("loginState",0);
                    editor.clear();
                    editor.apply();
                }
                Intent intent = new Intent("scos.intent.action.SCOSMAIN");
                intent.addCategory("scos.intent.category.SCOSLAUNCHER");
                startActivity(intent);
            }
        });


    }


    @Override
    public void onClick(final View v) {

        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run(){
                for(int i =0;i<2;i++)  {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Message message = new Message();
                message.what = UPDATE_V;
                handler.sendMessage(message);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url=HttpUtilsHttpURLConnection.BASE_URL+"/LoginValidator";
                Map<String, String> params = new HashMap<String, String>();
                String name=editTextName.getText().toString();
                String password=editTextPwd.getText().toString();
                params.put("name",name);
                params.put("password",password);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url,params);
                Message msg = new Message();
                msg.what=0x12;
                Bundle data=new Bundle();
                data.putString("result",result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            @SuppressLint("HandlerLeak")
            Handler hander = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what==0x12){
                        Bundle data1 = msg.getData();
                        String key = data1.getString("result");//得到json返回的json
                        try {
                            JSONObject json= new JSONObject(key);
                            int resultCode = (int) json.get("RESULTCODE");
                            if (resultCode==1){
                                User loginUser = new User();
                                loginUser.setUserName(editTextName.getText().toString());
                                loginUser.setPassword(editTextPwd.getText().toString());
                                String data = "";
                                switch (v.getId())
                                {
                                    case R.id.button_login:
                                        loginUser.setOldUser(true);
                                        data = "LoginSuccess";
                                        break;
                                    case R.id.button_register:
                                        loginUser.setOldUser(false);
                                        data = "RegisterSuccess";
                                        break;
                                }
                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userName",editTextName.getText().toString());
                                editor.putString("userPwd",editTextPwd.getText().toString());
                                editor.putInt("loginState",1);
                                editor.apply();
                                Intent intent = new Intent(LoginOrRegister.this,MainScreen.class);
                                intent.putExtra("extra_data", data);
                                intent.putExtra("user_info", loginUser);
                                startActivity(intent);
                            }else if(resultCode==2){
                                editTextName.setError("输入内容不符合规则");
                            }else if(resultCode==3){
                                editTextPwd.setError("输入内容不符合规则");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }).start();

    }

}
