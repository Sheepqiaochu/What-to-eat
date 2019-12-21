package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.eatwhat.Login.JSON;

//账号管理
public class ClientInfo extends AppCompatActivity {

    private String newUserphone;
    private String newUsername;
    EditText et_newUserphone;
    EditText getEt_newUsername;
    boolean done=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        et_newUserphone=(EditText)findViewById(R.id.et_new_phone);
        getEt_newUsername=(EditText)findViewById(R.id.et_new_username);

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        ImageButton buttonReturn=findViewById(R.id.button_return3);

        //点击返回
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到“我的”的页面
                //Intent intent=new Intent(ClientInfo.this,ClientActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        //点击提交确认

        Button buttonChange=(Button)findViewById(R.id.bt_change_submit);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();

                if(!TextUtils.isEmpty(newUserphone)) {
                    Toast.makeText(ClientInfo.this,"修改了用户名(手机号)",Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(newUsername)) {
                    Toast.makeText(ClientInfo.this,"修改了用户昵称",Toast.LENGTH_SHORT).show();
                }
                //**************************************
                //注：并没有把值传过去
                //**************************************

                sendRequestWithOkHttp();

                synchronized (ClientInfo.this) {
                    try {
                        ClientInfo.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //跳转到“我的”页面
                if(done) {
                    Toast.makeText(ClientInfo.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClientInfo.this, ClientActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ClientInfo.this, "修改失败！", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void getEditString()
    {
        newUserphone=et_newUserphone.getText().toString().trim();
        newUsername=getEt_newUsername.getText().toString().trim();
    }


    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (ClientInfo.this){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    if(newUsername.length()==0||newUserphone.length()==0) {
                        ClientInfo.this.notifyAll();
                        throw new Exception();
                    }
                    OkHttpClient client=new OkHttpClient();
                    //connection.setRequestMethod("POST");
                    Map map=new HashMap<>();
                    map.put("uid",MyApplication.user.getUid());
                    map.put("phoneNumber",newUserphone);
                    map.put("name",newUsername);
                    String param=new Gson().toJson(map);
                    RequestBody body=RequestBody.create(JSON,param);
                    //connection.setRequestMethod("POST");
                    Request request=new Request.Builder()
                            //.url("http://101.201.56.86:90/getAllDishes")
                            .url("http://101.201.56.86:90/changeInfo")   //服务器的json地址，到时候改这里
                            .post(body)
                            .build();

                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    MyApplication.user.setPhoneNumber(newUserphone);
                    MyApplication.user.setName(newUsername);

                    parseJSONWithGSON(responseData);        //用gson处理json数据
                }catch (Exception e){
                    e.printStackTrace();
                }
            }}
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){    //用gson处理json数据

        done=true;
        ClientInfo.this.notifyAll();
        /*Gson gson=new Gson();
        List<RecommendFood> hImgList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
            {}.getType());
        int i=0;
        for(RecommendFood hImg:hImgList){
            //Log.d("MainActivity","id is "+app.getId());
            recommendFood[i++]=hImg;
            //foodimages[i++]=hImg.getImageId();     //设置foodimages[]图像源
        }*/



    }
}
