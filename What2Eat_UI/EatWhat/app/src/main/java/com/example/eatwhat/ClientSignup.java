package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class ClientSignup extends AppCompatActivity {

    private EditText etUser;//用户名
    private EditText etPassword;//密码
    private EditText etPassword2;//重复输入的密码
    private String user,password,password2;//上面三个控件的获得值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup);

        //隐藏默认标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        etUser=(EditText)findViewById(R.id.et_register_username);
        etPassword=(EditText)findViewById(R.id.et_register_pwd_input);
        etPassword2=(EditText)findViewById(R.id.et_register_pwd_input2);


        //点击返回 回到上一页（此时是“我的”界面）
        //顶端返回按钮
        ImageButton returnbutton=(ImageButton)findViewById(R.id.button_return2);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加点击事件
                Intent intent=new Intent(ClientSignup.this,Login.class);
                startActivity(intent);
            }
        });


        //勾选框
        final CheckBox checkBox=(CheckBox)findViewById(R.id.cb_protocol);

        //点击确认注册
        //确认注册按钮
        //***************************************
        /*
        Button buttonRegisterSubmit=(Button)findViewById(R.id.button_submit);
        buttonRegisterSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //获取输入框中内容
                getEditString();
                //判断输入框中内容
                if(TextUtils.isEmpty(user)) {
                    Toast.makeText(ClientSignup.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)) {
                    Toast.makeText(ClientSignup.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password2)) {
                    Toast.makeText(ClientSignup.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!password.equals(password2)) {
                    Toast.makeText(ClientSignup.this,"两次输入的密码不同",Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(user)) {
                    Toast.makeText(ClientSignup.this,"此用户已存在",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ClientSignup.this,"注册成功辣",Toast.LENGTH_SHORT).show();
                    //注册成功，所以把账号密码等保存到sp里
                    saveRegisterInfo(user,password);

                    if(checkBox.isChecked())
                    {
                        Intent intent=new Intent(ClientSignup.this,ClientRatefood.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ClientSignup.this,"请勾选同意条款后再注册",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        */
        //****************************************
        //测试用
        //*****************************************
        Button buttonRegisterSubmit=(Button)findViewById(R.id.button_submit);
        buttonRegisterSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {



                    if(checkBox.isChecked())
                    {
                        Toast.makeText(ClientSignup.this,"注册成功辣",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ClientSignup.this,ClientRatefood.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ClientSignup.this,"请勾选同意条款后再注册",Toast.LENGTH_SHORT).show();
                    }
                }


        });



    }
    //获取控件输入框的内容
    private void getEditString()
    {
        user=etUser.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        password2=etPassword2.getText().toString().trim();
    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }

    //保存账号密码到SharedPreferences中SharedPreferences
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}
