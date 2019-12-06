package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

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

public class RecommendActivity extends AppCompatActivity {

    private List<RecommendFood> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ActionBar actionbar=getSupportActionBar();  //隐藏默认标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        //sendRequestWithOkHttp();    //通过okhttp传送请求
        initFoods();   //初始化食物们
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //StaggeredGridLayoutManager layoutManager=
        //        new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(RecommendActivity.this,foodList);
        recyclerView.setAdapter(adapter);

        ActivityCollector.addActivity(this);    //加入活动到活动收集器中

        TextView textview = (TextView) findViewById(R.id.top_recommend);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);
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

    private void initFoods() {         //初始化食物们，到时候没用了可以删掉
        //for (int i = 0; i < 2; i++) {
            RecommendFood apple = new RecommendFood(getRandomLengthName("Apple"), R.drawable.apple_pic);
            foodList.add(apple);
            RecommendFood banana = new RecommendFood(getRandomLengthName("Banana"), R.drawable.banana_pic);
            foodList.add(banana);
            RecommendFood orange = new RecommendFood(getRandomLengthName("Orange"), R.drawable.orange_pic);
            foodList.add(orange);
            RecommendFood watermelon = new RecommendFood(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            foodList.add(watermelon);
            RecommendFood pear = new RecommendFood(getRandomLengthName("Pear"), R.drawable.pear_pic);
            foodList.add(pear);
            RecommendFood grape = new RecommendFood(getRandomLengthName("Grape"), R.drawable.grape_pic);
            foodList.add(grape);
            RecommendFood pineapple = new RecommendFood(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            foodList.add(pineapple);
            RecommendFood strawberry = new RecommendFood(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            foodList.add(strawberry);
            RecommendFood cherry = new RecommendFood(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
            foodList.add(cherry);
            RecommendFood mango = new RecommendFood(getRandomLengthName("Mango"), R.drawable.mango_pic);
            foodList.add(mango);
       // }
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
                    Request request=new Request.Builder()
                            .url("http://10.0.2.2/RecommendFood.json")   //服务器的json地址，到时候改这里
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
        List<RecommendFood> rFoodList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
            {}.getType());
        for(RecommendFood rFood:rFoodList){

            //RecommendFood mango = new RecommendFood(getRandomLengthName("Mango"), R.drawable.mango_pic);
            foodList.add(rFood);
        }
    }
}
