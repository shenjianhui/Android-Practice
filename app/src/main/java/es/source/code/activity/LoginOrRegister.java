package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;

public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button button1;
    private Button button_reg;
    private ProgressBar progressBar;
    private EditText editTextName;
    private EditText editTextPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);
        button = (Button)findViewById(R.id.button_login);
        button_reg = findViewById( R.id.button_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_login);
        button.setOnClickListener(this);
        button_reg.setOnClickListener(listener);
        button1 = findViewById(R.id.button_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = "Return";
                //Intent intent = new Intent(LoginOrRegister.this,MainScreen.class);
                Intent intent = new Intent("scos.intent.action.SCOSMAIN");
                intent.addCategory("scos.intent.category.SCOSLAUNCHER");
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });
        editTextName = findViewById(R.id.editText_username);
        editTextPwd = findViewById(R.id.editText_pwd);

    }

    Button.OnClickListener listener = new Button.OnClickListener(){
        public void onClick(View v){
            progressBar.setVisibility(View.VISIBLE);
            new Thread() {
                public void run() {
                    int time = 5;
                    while (time < 11) {
                        time = time + 5;
                        try {
                            //加一次所需要的时间
                            TimeUnit.SECONDS.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }.start();

            String name = editTextName.getText().toString();
            String pwd = editTextPwd.getText().toString();
            Check1(name,pwd);

        }
    };

    public void Check1(String name,String pwd){
        String strRegex = "^[0-9A-Za-z]+$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher1 = pattern.matcher(name);
        Matcher matcher2 = pattern.matcher(pwd);
        boolean mt1 = matcher1.find();
        boolean mt2 = matcher2.find();
        if(mt1&&mt2){
            User loginUser = new User();
            loginUser.setUserName(name);
            loginUser.setPassword(pwd);
            loginUser.setOldUser(false);
            String data = "RegisterSuccess";
            Intent intent = new Intent(this,MainScreen.class);
            intent.putExtra("extra_data", data);
            intent.putExtra("user_info", loginUser);
            startActivity(intent);
        }else{
            if (!mt1) {
                editTextName.setError("输入内容不符合规则");
            }
            if (!mt2) {
                editTextPwd.setError("输入内容不符合规则");
            }
        }
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        new Thread() {
            public void run() {
                int time = 5;
                while (time < 11) {
                    time = time + 5;
                    try {
                        //加一次所需要的时间
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }.start();

        String name = editTextName.getText().toString();
        String pwd = editTextPwd.getText().toString();
        Check(name,pwd);

    }

    public void Check(String name,String pwd){
        String strRegex = "^[0-9A-Za-z]+$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher1 = pattern.matcher(name);
        Matcher matcher2 = pattern.matcher(pwd);
        boolean mt1 = matcher1.find();
        boolean mt2 = matcher2.find();
        if(mt1&&mt2){
            User loginUser = new User();
            loginUser.setUserName(name);
            loginUser.setPassword(pwd);
            loginUser.setOldUser(true);
            String data = "LoginSuccess";
            Intent intent = new Intent(this,MainScreen.class);
            intent.putExtra("extra_data", data);
            intent.putExtra("user_info", loginUser);
            startActivity(intent);
        }else{
            if (!mt1) {
                editTextName.setError("输入内容不符合规则");
            }
            if (!mt2) {
                editTextPwd.setError("输入内容不符合规则");
            }
        }
    }
}
