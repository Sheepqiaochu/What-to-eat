package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private List<Function> functionList=new ArrayList<>();
    private User user; //这个变量user要传参传过来

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //“我的”中的功能列表的ListView
        initFunctions();//初始化功能的数据
        FunctionAdapter adapter=new FunctionAdapter(ClientActivity.this,R.layout.function_item,functionList);

        ListView listView=(ListView) findViewById((R.id.list_function));
        listView.setAdapter(adapter);


        user=(User)getIntent().getSerializableExtra("user");
        if(user!=null) {
            TextView usernameText = (TextView) findViewById(R.id.text_username);   //显示用户昵称、手机号
            usernameText.setText(user.getName());
            TextView phoneText = (TextView) findViewById(R.id.text_phone);   //显示用户昵称、手机号
            //phoneText.setText("69779110");
            phoneText.setText(user.getPhoneNumber());
            //**********************************
            /*
            phoneText.post(new Runnable() {
                @Override
                public void run() {
                    phoneText.setText("69779110");
                }
            });
            */
            //*********************************
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function function=functionList.get(position);
                //Toast.makeText(ClientActivity.this,function.getName(),Toast.LENGTH_SHORT).show();
                if(function.getName()=="用户登录/注册")
                {
                    Intent intent=new Intent(ClientActivity.this,Login.class);
                    startActivity(intent);
                }
                //************************************************
                //注：暂时拿我的评价点进去当评分界面了
                if(function.getName()=="我的评价")
                {
                    Intent intent=new Intent(ClientActivity.this,ClientRatefood.class);
                    startActivity(intent);
                }
                //*************************************************
                if(function.getName()=="账号管理")
                {
                    Intent intent=new Intent(ClientActivity.this,ClientInfo.class);
                    startActivity(intent);
                }
            }
        });

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        TextView textview = (TextView) findViewById(R.id.top_client);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);
        ActivityCollector.addActivity(this);

        ActivityCollector.addActivity(this);
    }


    private void initFunctions() {
        for(int i=0;i<1;i++) {
            Function fun1=new Function("用户登录/注册",R.drawable.fun1_pic);
            functionList.add(fun1);
            Function fun2=new Function("我的评价",R.drawable.fun2_pic);
            functionList.add(fun2);
            /*
            Function fun3=new Function("我的收藏",R.drawable.fun3_pic);
            functionList.add(fun3);
            Function fun4=new Function("饮食习惯",R.drawable.fun4_pic);
            functionList.add(fun4);
            */
            Function fun5=new Function("账号管理",R.drawable.fun5_pic);
            functionList.add(fun5);
        }
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
}
