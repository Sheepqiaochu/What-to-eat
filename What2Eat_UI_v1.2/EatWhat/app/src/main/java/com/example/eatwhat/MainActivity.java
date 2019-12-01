package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private int[] foodimages = new int[]{   //食物图片
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3,
    };
    private int refreshtime=0;  //刷新次数
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar=getSupportActionBar();  //隐藏原来的标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        Button button=(Button)findViewById(R.id.button_refresh);
        imageView=(ImageView)findViewById(R.id.image_food);
        button.setOnClickListener(this);


        TextView textview = (TextView) findViewById(R.id.top_menu);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);
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
            default:
                break;
        }
    }
}
