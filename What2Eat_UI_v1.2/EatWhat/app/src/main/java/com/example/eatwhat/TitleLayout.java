package com.example.eatwhat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TitleLayout extends LinearLayout {
    public TitleLayout(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_title, this);

        Button title_1=(Button)findViewById(R.id.title_1home);
        title_1.setBackgroundResource(R.drawable.title1);
        Button title_2=(Button)findViewById(R.id.title_2recommend);
        title_2.setBackgroundResource(R.drawable.title2);
        Button title_3=(Button)findViewById(R.id.title_3client);
        title_3.setBackgroundResource(R.drawable.title3);

        title_1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                Intent intent=new Intent("com.example.eatwhat.ACTION_1");
                context.startActivity(intent);
            }
        });
        title_2.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                Intent intent=new Intent("com.example.eatwhat.ACTION_2");
                context.startActivity(intent);
            }
        });
        title_3.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                Intent intent=new Intent("com.example.eatwhat.ACTION_3");
                context.startActivity(intent);
            }
        });
    }
}
