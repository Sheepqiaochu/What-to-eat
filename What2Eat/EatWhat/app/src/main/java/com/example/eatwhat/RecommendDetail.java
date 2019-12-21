package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.yinglan.shadowimageview.ShadowImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.eatwhat.Login.JSON;

public class RecommendDetail extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private RecommendFood food; //这个变量food要传参传过来
    private int page; //这个变量page要传参传过来

    public RecommendDetail(){}
    public RecommendDetail(RecommendFood food){
        this.food = food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);

        //隐藏默认标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        food=(RecommendFood)getIntent().getSerializableExtra("food");
        page=(Integer)getIntent().getSerializableExtra("page");

        /*
        imageView=(ImageView)findViewById(R.id.image_recommend_detail); //设置图像源
        imageView.setImageResource(food.getImageId());
        */
        ShadowImageView imageView;
        imageView=(ShadowImageView)findViewById(R.id.image_recommend_detail); //设置图像源
        imageView.setImageResource(food.getImageId());

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        try{
            Bitmap bitmap=getBitmap(food.getImageUrl());
            imageView.setImageBitmap(bitmap);
        }catch(IOException e){
            e.printStackTrace();
        }

        textView=(TextView)findViewById(R.id.tv_fooddetail);    //设置标题
        textView.setText(food.getName()+"详细介绍");

        textView=(TextView)findViewById(R.id.text_recommend_name);    //设置名称源
        textView.setText(food.getName());
        textView=(TextView)findViewById(R.id.text_recommend_price);    //设置价格源
        textView.setText("$"+food.getPrice());
        //textView=(TextView)findViewById(R.id.text_recommend_excellence);    //设置推荐度源
        //textView.setText(food.getExcellence()+"%");
        textView=(TextView)findViewById(R.id.text_recommend_detail);    //设置详细介绍源
        textView.setText(food.getDetail());

        //********************************************
        //新加的收藏部分

        ShineButton starButton;
        starButton=findViewById(R.id.bt_star);


        //暂时没favorite先注释掉了
        if(MyApplication.user.getUid()!=-1&&food.getFavorite()==true) {
            starButton.setChecked(true);
        }
        else {//这个其实也可以去掉 我搁着以防万一一下
            starButton.setChecked(false);
        }



        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starButton.isChecked()) {

                    sendRequestWithOkHttp();
                    synchronized (RecommendDetail.this) {
                        try {
                            RecommendDetail.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if(MyApplication.user.getUid()!=-1)
                        Toast.makeText(RecommendDetail.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RecommendDetail.this, "登录以使用收藏功能", Toast.LENGTH_SHORT).show();
                    //****************************************
                    //调用ClientStar里面的addToList给加到listView里面
                    //****************************************
                }
                else {

                    sendRequestWithOkHttp_2();
                    synchronized (RecommendDetail.this) {
                        try {
                            RecommendDetail.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if(MyApplication.user.getUid()!=-1)
                        Toast.makeText(RecommendDetail.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RecommendDetail.this, "登录以使用收藏功能", Toast.LENGTH_SHORT).show();
                    //****************************************
                    //调用ClientStar里面的removeFromList给删除掉
                    //****************************************
                }
            }
        });
        //********************************************

        ImageButton returnbutton=(ImageButton)findViewById(R.id.button_return);    //按返回按钮

        returnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                /*if(page==0) { //返回首页
                    Intent intent = new Intent(RecommendDetail.this, MainActivity.class);
                    startActivity(intent);
                }
                else{   //返回探索页
                    Intent intent = new Intent(RecommendDetail.this, RecommendActivity.class);
                    startActivity(intent);
                }*/
                finish();
                //Intent intent=new Intent("com.example.eatwhat.ACTION_2");
                //startActivity(intent);
            }
        });
    }

    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (RecommendDetail.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();

                        if(MyApplication.user.getUid()==-1){
                            RecommendDetail.this.notifyAll();
                            throw new Exception();
                        }

                        Map map=new HashMap<>();
                        map.put("uid",MyApplication.user.getUid());
                        map.put("did",food.getDid());
                        String param=new Gson().toJson(map);
                        RequestBody body=RequestBody.create(JSON,param);
                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                .url("http://101.201.56.86:90/insertFavorite")   //服务器的json地址，到时候改这里
                                .post(body)
                                .build();
                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        RecommendDetail.this.notifyAll();
                        //parseJSONWithJSON(responseData);        //用json处理json数据
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }}
        }).start();
    }

    private void sendRequestWithOkHttp_2(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (RecommendDetail.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();

                        Map map=new HashMap<>();
                        map.put("uid",MyApplication.user.getUid());
                        map.put("did",food.getDid());
                        String param=new Gson().toJson(map);
                        RequestBody body=RequestBody.create(JSON,param);
                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                .url("http://101.201.56.86:90/deleteFavorite")   //服务器的json地址，到时候改这里
                                .post(body)
                                .build();
                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        RecommendDetail.this.notifyAll();
                        //parseJSONWithJSON(responseData);        //用json处理json数据
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }}
        }).start();
    }

}
