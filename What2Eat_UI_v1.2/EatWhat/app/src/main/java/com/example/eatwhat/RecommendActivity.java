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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        initFoods();   //食物们
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

    private void initFoods() {         //初始化食物们
        for (int i = 0; i < 1; i++) {
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
        }
    }

    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++)
            builder.append(name);
        return builder.toString();
    }
}
