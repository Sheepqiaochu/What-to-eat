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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //“我的”中的功能列表的ListView
        initFunctions();//初始化功能的数据
        FunctionAdapter adapter=new FunctionAdapter(ClientActivity.this,R.layout.function_item,functionList);

        ListView listView=(ListView) findViewById((R.id.list_function));
        listView.setAdapter(adapter);

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
            Function fun2=new Function("Function2",R.drawable.fun2_pic);
            functionList.add(fun2);
            Function fun3=new Function("Function3",R.drawable.fun3_pic);
            functionList.add(fun3);
            Function fun4=new Function("Function4",R.drawable.fun4_pic);
            functionList.add(fun4);
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
