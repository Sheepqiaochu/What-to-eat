package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatwhat.MD5Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//https://www.jianshu.com/p/fa6ecb9a8175

public class Login extends AppCompatActivity {

    private String user,psw,spPsw;//获取的用户名，密码，加密密码
    private EditText etUser,etPsw;
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    User auser;
    boolean permit=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //点击返回 回到上一页（此时是“我的”界面）
        //顶端返回按钮

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        ImageButton returnbutton=(ImageButton)findViewById(R.id.button_return2);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加点击事件
                Intent intent=new Intent(Login.this,ClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        etUser=(EditText)findViewById(R.id.et_login_username);
        etPsw=(EditText)findViewById(R.id.et_login_pwd);
        Button buttonSubmit=(Button)findViewById(R.id.bt_login_submit);
        Button buttonRegister=(Button)findViewById(R.id.bt_login_register);

        //点击注册按钮
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册的页面
                Intent intent=new Intent(Login.this,ClientSignup.class);
                startActivity(intent);
            }
        });

        //点击登录
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //跳转到首页 就是推荐菜的那个，所以要把启动的首页改成这个
                Intent intent=new Intent("com.example.eatwhat.ACTION_1");
                startActivity(intent);
                */
                user=etUser.getText().toString().trim();
                psw=etPsw.getText().toString().trim();

                sendRequestWithOkHttp();    //通过okhttp传送请求

                if(permit) {
                    Toast.makeText(Login.this, "登陆成功辣", Toast.LENGTH_SHORT).show();
                    Login.this.finish();
                    ;
                    Intent intent = new Intent(Login.this, ClientActivity.class);
                    intent.putExtra("user", auser);   //传参
                    startActivity(intent);
                    return;
                }

                //后面的放弃？
/*
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw= MD5Utils.md5(psw);
                // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw=readPsw(user);

                //对用户名和密码进行检查
                if(TextUtils.isEmpty(user)) {
                    Toast.makeText(Login.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)) {
                    Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spPsw)) {//判断密码是否相等
                    Toast.makeText(Login.this,"登陆成功辣",Toast.LENGTH_SHORT).show();
                    //保存登陆状态
                    saveLoginStatus(true,user);
                    //********************************************
                    //暂时先让他进入“我的”界面了
                    //********************************************
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登陆界面
                    Login.this.finish();;
                    startActivity(new Intent(Login.this,ClientActivity.class));
                    return;
                }else if(spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw)) {
                    Toast.makeText(Login.this,"密码错误！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(Login.this,"莫得此用户",Toast.LENGTH_SHORT).show();
                }*/

            }
        });


    }

    //从SharedPreferences中根据用户名读取密码
    private String readPsw(String userName) {
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }
    //保存登陆状态和登陆用户名到Sp中
    private void saveLoginStatus(boolean status,String userName) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }
    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            //获取注册界面传过来的用户名
            String userName=data.getStringExtra("user");
            if(!TextUtils.isEmpty(userName)) {
                etUser.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                etUser.setSelection(userName.length());
            }
        }
    }



    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    OkHttpClient client=new OkHttpClient();

                    /*RequestBody body= new FormBody.Builder()
                            .add("phoneNumber","12345678901")
                            .add("password","123456")
                            .build();*/

                    Map map=new HashMap<>();
                    map.put("phoneNumber",user);
                    map.put("password",psw);
                    String param=new Gson().toJson(map);
                    RequestBody body=RequestBody.create(JSON,param);
                    //connection.setRequestMethod("POST");
                    Request request=new Request.Builder()
                            .url("http://101.201.56.86:90/login")   //服务器的json地址，到时候改这里
                            .post(body)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithJSON(responseData);        //用json处理json数据
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSON(String jsonData){    //用json处理json数据
        try{
            JSONObject jsonObject=new JSONObject(jsonData).getJSONObject("data");
            auser=new User();
            auser.setPhoneNumber(jsonObject.getString("phoneNumber"));
            auser.setPassword(jsonObject.getString("password"));
            auser.setName(jsonObject.getString("name"));
            auser.setUid(jsonObject.getInt("uid"));

        }catch (Exception e){
            e.printStackTrace();
        }

        /*Gson gson=new Gson();
        Respdata rdata=gson.fromJson(jsonData,Respdata.class);*/
        if(auser.getUid()!=-1){
            //允许登录
            permit=true;
            /*Toast.makeText(Login.this,"登陆成功辣",Toast.LENGTH_SHORT).show();
            Login.this.finish();;
            Intent intent=new Intent(Login.this,ClientActivity.class);
            intent.putExtra("user",auser);   //传参
            startActivity(intent);*/
        }
        else{
            Toast.makeText(Login.this,"登陆失败",Toast.LENGTH_SHORT).show();
        }

        /*List<User> rUserList=gson.fromJson(jsonData,new TypeToken<List<User>>()
        {}.getType());
        for(User rUser:rUserList){
            //RecommendFood mango = new RecommendFood(getRandomLengthName("Mango"), R.drawable.mango_pic);
            //foodList.add(rFood);
            if(user==rUser.getPhoneNumber()&&psw==rUser.getPassword())
            {
                //允许登录
                Toast.makeText(Login.this,"登陆成功辣",Toast.LENGTH_SHORT).show();
            }
        }*/
    }


}
