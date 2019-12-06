package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

//注册之后对菜评分
public class ClientRatefood extends AppCompatActivity {

    public float rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ratefood);


        //隐藏默认标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        //设置打分的星星的一些内容
        ScaleRatingBar ratingBar = new ScaleRatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setRating(3);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);
        ratingBar.setStarWidth(105);
        ratingBar.setStarHeight(105);
        ratingBar.setIsIndicator(false);
        ratingBar.setClickable(true);
        ratingBar.setScrollable(true);
        ratingBar.setClearRatingEnabled(true);
        //ratingBar.setEmptyDrawableRes(R.drawable.start_empty);
        //ratingBar.setFilledDrawableRes(R.drawable.start_empty);
        //ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //俺还没有开始传值
            }
        });

        Button buttonRateSubmit=(Button)findViewById(R.id.bt_rateSubmit);
        buttonRateSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientRatefood.this,"评分提交成功！（但是还没有处理数据）",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ClientRatefood.this,ClientActivity.class);
                startActivity(intent);

            }
        });

    }
}
