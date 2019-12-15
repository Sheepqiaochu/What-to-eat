package com.example.eatwhat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TitleLayout extends LinearLayout implements RadioGroup.OnCheckedChangeListener{
    Context context;
    RadioButton rb;
    static int currentid;
    static boolean firsttime=true;

    public TitleLayout(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_title, this);

        this.context=context;
        RadioGroup rg_tab_bar;


        if(firsttime) {
            currentid=R.id.rb_home;
            firsttime = false;
        }

        rb = findViewById(currentid);
        rb.setChecked(true);

        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Intent intent;
        currentid=i;
        switch (i){
            case R.id.rb_home:
                intent=new Intent("com.example.eatwhat.ACTION_1");
                context.startActivity(intent);
                break;
            case R.id.rb_recommend:
                intent=new Intent("com.example.eatwhat.ACTION_2");
                context.startActivity(intent);
                break;
            case R.id.rb_client:
                intent=new Intent("com.example.eatwhat.ACTION_3");
                context.startActivity(intent);
                break;
        }
    }
}
