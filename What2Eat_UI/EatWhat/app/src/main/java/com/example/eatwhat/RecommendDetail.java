package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.yinglan.shadowimageview.ShadowImageView;

public class RecommendDetail extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private RecommendFood food; //这个变量food要传参传过来
    private int page; //这个变量page要传参传过来

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

        food=(RecommendFood)getIntent().getSerializableExtra("food");
        page=(Integer)getIntent().getSerializableExtra("page");

        /*
        imageView=(ImageView)findViewById(R.id.image_recommend_detail); //设置图像源
        imageView.setImageResource(food.getImageId());
        */
        ShadowImageView imageView;
        imageView=(ShadowImageView)findViewById(R.id.image_recommend_detail); //设置图像源
        imageView.setImageResource(food.getImageId());

        textView=(TextView)findViewById(R.id.tv_fooddetail);    //设置标题
        textView.setText(food.getName()+"详细介绍");

        textView=(TextView)findViewById(R.id.text_recommend_name);    //设置名称源
        textView.setText(food.getName());
        textView=(TextView)findViewById(R.id.text_recommend_price);    //设置价格源
        textView.setText("$"+food.getPrice());
        textView=(TextView)findViewById(R.id.text_recommend_excellence);    //设置推荐度源
        textView.setText(food.getExcellence()+"%");
        textView=(TextView)findViewById(R.id.text_recommend_detail);    //设置详细介绍源
        textView.setText(food.getDetail());

        ImageButton returnbutton=(ImageButton)findViewById(R.id.button_return);    //按返回按钮

        returnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //添加点击事件
                if(page==0) { //返回首页
                    Intent intent = new Intent(RecommendDetail.this, MainActivity.class);
                    startActivity(intent);
                }
                else{   //返回探索页
                    Intent intent = new Intent(RecommendDetail.this, RecommendActivity.class);
                    startActivity(intent);
                }
                //Intent intent=new Intent("com.example.eatwhat.ACTION_2");
                //startActivity(intent);
            }
        });
    }
}
