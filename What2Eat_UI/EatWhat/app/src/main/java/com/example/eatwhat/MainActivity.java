package com.example.eatwhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.yinglan.shadowimageview.ShadowImageView;
import com.sackcentury.shinebuttonlib.ShineButton;
import ezy.ui.view.RoundButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ShadowImageView imageView;
    private TextView textView;
    private RecommendFood[] recommendFood=new RecommendFood[3];//假装推荐3个
    private RecommendFood currentFood;  //当前显示的食物
    /*private int[] foodimages = new int[]{   //食物图片，暂时是从文件夹里弄来的
            R.drawable.apple_pic,
            R.drawable.banana_pic,
            R.drawable.cherry_pic
    };*/
    private int refreshtime=0;  //刷新次数
    public int numLike=0;//这个是like按钮被点击的次数，偶数说明时未被选中状态
    int numDislike=0;
    ShineButton likeButton;
    ShineButton dislikeButton;

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar=getSupportActionBar();  //隐藏原来的标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        recommendFood[0]=new RecommendFood("apple",getRandomLengthName("Apple"), R.drawable.apple_pic,10,1,1.3,1.3,1.3);           //临时初始化，到时候删掉
        recommendFood[1]=new RecommendFood("banana",getRandomLengthName("Banana"), R.drawable.banana_pic,8,2,1.6,2.9,6.6);
        recommendFood[2]=new RecommendFood("orange",getRandomLengthName("Orange"), R.drawable.orange_pic,6,3,3.9,4.4,8.7);

        //sendRequestWithOkHttp();    //通过okhttp传送请求
        currentFood=recommendFood[0];

        RoundButton button=(RoundButton)findViewById(R.id.button_refresh) ;
        //Button button=(Button)findViewById(R.id.button_refresh);
        //修改的图片部分
        //***********************
        //imageView=(ImageView)findViewById(R.id.image_food);
        imageView=(ShadowImageView)findViewById(R.id.image_food);
        imageView.setImageResource(currentFood.getImageId());
        imageView.setOnClickListener(this);
        textView=(TextView)findViewById(R.id.tv_foodname);
        textView.setText(currentFood.getName());
        textView.setOnClickListener(this);
        button.setOnClickListener(this);


        TextView textview = (TextView) findViewById(R.id.top_menu);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);

        //like & dislike
        //******************************************************
        //从这里可以直接修改推荐度还是？？？？
        //******************************************************

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
                currentFood=recommendFood[refreshtime];
                imageView.setImageResource(recommendFood[refreshtime].getImageId());
                textView.setText(recommendFood[refreshtime].getName());
                break;
            case R.id.bt_like:      //点击like按钮
                //调用setExcellence函数改变正在推荐的食物的推荐度
                //*********************************************

                //*********************************************
                numLike++;
                //使只能单选
                if(numLike%2==0)    //like未被选中
                    dislikeButton.setEnabled(true);
                else//选中like，不能点dislike
                    dislikeButton.setEnabled(false);
                break;
            case R.id.bt_dislike:   //点击dislike按钮
                //调用setExcellence函数改变正在推荐的食物的推荐度
                //*********************************************

                //*********************************************
                numDislike++;
                //使只能单选
                if(numDislike%2==0)    //like未被选中
                    likeButton.setEnabled(true);
                else//选中like，不能点dislike
                    likeButton.setEnabled(false);
                break;
            case R.id.image_food:
            case R.id.tv_foodname:
                Intent intent=new Intent(MainActivity.this,RecommendDetail.class);
                intent.putExtra("food",currentFood);   //传参
                intent.putExtra("page",0);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private String getRandomLengthName(String name){    //这个到时候没用了可以删掉
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++)
            builder.append(name);
        return builder.toString();
    }

    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    OkHttpClient client=new OkHttpClient();
                    connection.setRequestMethod("POST");
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
        List<RecommendFood> hImgList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
            {}.getType());
        int i=0;
        for(RecommendFood hImg:hImgList){
            //Log.d("MainActivity","id is "+app.getId());
            recommendFood[i++]=hImg;
            //foodimages[i++]=hImg.getImageId();     //设置foodimages[]图像源
        }
    }

}
