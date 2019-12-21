package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ClientStar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    public static List<RecommendFood> foodStaredList=new ArrayList<>();

    //暂时想的加一个这个函数，然后点星星的时候调用这个？
    /*public void addToList(RecommendFood foodNew) {
        RecommendFood food=new RecommendFood(foodNew.getName(),foodNew.getImageId());
        foodStaredList.add(food);
    }*/
    //从list中查找删除对应的收藏
    public void removeFromList(RecommendFood food) {
        for(int i=0;i<foodStaredList.size();i++) {
            if(foodStaredList.get(i).getName()==food.getName()) {
                foodStaredList.remove(i);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_star);

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(ClientStar.this,foodStaredList);
        recyclerView.setAdapter(adapter);


        ImageButton returnbutton=(ImageButton)findViewById(R.id.button_return4);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加点击事件
                Intent intent=new Intent(ClientStar.this,ClientActivity.class);
                startActivity(intent);
            }
        });
    }


}
