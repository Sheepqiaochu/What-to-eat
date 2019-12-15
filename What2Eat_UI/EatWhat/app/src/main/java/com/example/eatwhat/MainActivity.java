package com.example.eatwhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.yinglan.shadowimageview.ShadowImageView;
import com.sackcentury.shinebuttonlib.ShineButton;
import ezy.ui.view.RoundButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ShadowImageView imageView;
    private int[] foodimages = new int[]{   //食物图片，暂时是从文件夹里弄来的
            R.drawable.apple_pic,
            R.drawable.banana_pic,
            R.drawable.cherry_pic
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

        RoundButton button=(RoundButton)findViewById(R.id.button_refresh) ;
        //Button button=(Button)findViewById(R.id.button_refresh);
        //修改的图片部分
        //***********************
        //imageView=(ImageView)findViewById(R.id.image_food);
        imageView=(ShadowImageView)findViewById(R.id.image_food);
        button.setOnClickListener(this);


        TextView textview = (TextView) findViewById(R.id.top_menu);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);

        //like & dislike
        //******************************************************
        //从这里可以直接修改推荐度还是？？？？
        //******************************************************
        ShineButton likeButton;
        ShineButton dislikeButton;

        likeButton=findViewById(R.id.bt_like);
        dislikeButton=findViewById(R.id.bt_dislike);
        //修改ShineButton的底图
        dislikeButton.setShapeResource(R.drawable.dislike);

        //设置两个按钮的点击事件
        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);

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
            case R.id.bt_like:      //点击like按钮
                //调用setExcellence函数改变推荐度？？
                //假装有代码了
                break;
            case R.id.bt_dislike:   //点击dislike按钮
                //调用setExcellence函数改变推荐度？？
                //假装也有代码
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
