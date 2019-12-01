package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecommendDetail extends AppCompatActivity {

    RecommendFood food;

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

        Button returnbutton=(Button)findViewById(R.id.button_return1);    //按返回按钮

        returnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                Intent intent=new Intent("com.example.eatwhat.ACTION_2");
                startActivity(intent);
            }
        });
    }
}
