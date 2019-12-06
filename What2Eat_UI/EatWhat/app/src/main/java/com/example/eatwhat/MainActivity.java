package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private int[] foodimages = new int[]{   //食物图片，暂时是从文件夹里弄来的
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3,
    };
    private int refreshtime=0;  //刷新次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar=getSupportActionBar();  //隐藏原来的标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        //sendRequestWithOkHttp();    //通过okhttp传送请求

        Button button=(Button)findViewById(R.id.button_refresh);
        //修改的图片部分
        //***********************
        imageView=(ImageView)findViewById(R.id.image_food);
        button.setOnClickListener(this);


        TextView textview = (TextView) findViewById(R.id.top_menu);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            ActivityCollector.finishAll();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_refresh:   //点击刷新按钮
                refreshtime=(refreshtime+1)%3;
                imageView.setImageResource(foodimages[refreshtime]);
                break;
            default:
                break;
        }
    }

    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://10.0.2.2/HomePageImg.json")   //服务器的json地址，到时候改这里
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithGSON(responseData);        //用gson处理json数据
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){    //用gson处理json数据
        Gson gson=new Gson();
        List<HomePageImg> hImgList=gson.fromJson(jsonData,new TypeToken<List<HomePageImg>>()
            {}.getType());
        for(HomePageImg hImg:hImgList){
            //Log.d("MainActivity","id is "+app.getId());
            foodimages[hImg.getId()]=hImg.getImg();     //设置foodimages[]图像源
        }
    }

}
