package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//注册之后对菜评分
public class ClientRatefood extends AppCompatActivity {

    public float rate;
    User user;
    private RecommendFood[] rateFood=new RecommendFood[10];//十个待评分的菜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ratefood);


        //隐藏默认标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        user=(User)getIntent().getSerializableExtra("user");//传参
        sendRequestWithOkHttp();
        init();//暂时的初始化，到时候删掉

        //设置打分的星星的一些内容
        //评分1：麻辣烫
        ScaleRatingBar ratingBar1=(ScaleRatingBar)findViewById(R.id.simpleRatingBar1);
        ratingBar1.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating1=findViewById(R.id.tv_rating1);
                tv_rating1.setText("    你的评分是 "+rateNum+".");
                rateFood[0].setExcellence(rateNum);
            }
        });
        //评分2：糖醋里脊
        ScaleRatingBar ratingBar2=(ScaleRatingBar)findViewById(R.id.simpleRatingBar2);
        ratingBar2.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating2=findViewById(R.id.tv_rating2);
                tv_rating2.setText("    你的评分是 "+rateNum+".");
                rateFood[1].setExcellence(rateNum);
            }
        });
        //评分3：酸辣土豆丝
        ScaleRatingBar ratingBar3=(ScaleRatingBar)findViewById(R.id.simpleRatingBar3);
        ratingBar3.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating3=findViewById(R.id.tv_rating3);
                tv_rating3.setText("    你的评分是 "+rateNum+".");
                rateFood[2].setExcellence(rateNum);
            }
        });
        //评分4：牛肉酱拌面
        ScaleRatingBar ratingBar4=(ScaleRatingBar)findViewById(R.id.simpleRatingBar4);
        ratingBar4.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating4=findViewById(R.id.tv_rating4);
                tv_rating4.setText("    你的评分是 "+rateNum+".");
                rateFood[3].setExcellence(rateNum);
            }
        });
        //评分5：烤鸭饭
        ScaleRatingBar ratingBar5=(ScaleRatingBar)findViewById(R.id.simpleRatingBar5);
        ratingBar5.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating5=findViewById(R.id.tv_rating5);
                tv_rating5.setText("    你的评分是 "+rateNum+".");
                rateFood[4].setExcellence(rateNum);
            }
        });
        //评分6：羊肉小火锅
        ScaleRatingBar ratingBar6=(ScaleRatingBar)findViewById(R.id.simpleRatingBar6);
        ratingBar6.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating6=findViewById(R.id.tv_rating6);
                tv_rating6.setText("    你的评分是 "+rateNum+".");
                rateFood[5].setExcellence(rateNum);
            }
        });
        //评分7：绿豆汤
        ScaleRatingBar ratingBar7=(ScaleRatingBar)findViewById(R.id.simpleRatingBar7);
        ratingBar7.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating7=findViewById(R.id.tv_rating7);
                tv_rating7.setText("    你的评分是 "+rateNum+".");
                rateFood[6].setExcellence(rateNum);
            }
        });
        //评分8：烤冷面
        ScaleRatingBar ratingBar8=(ScaleRatingBar)findViewById(R.id.simpleRatingBar8);
        ratingBar8.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating8=findViewById(R.id.tv_rating8);
                tv_rating8.setText("    你的评分是 "+rateNum+".");
                rateFood[7].setExcellence(rateNum);
            }
        });
        //评分9：羊肉泡馍
        ScaleRatingBar ratingBar9=(ScaleRatingBar)findViewById(R.id.simpleRatingBar9);
        ratingBar9.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating9=findViewById(R.id.tv_rating9);
                tv_rating9.setText("    你的评分是 "+rateNum+".");
                rateFood[8].setExcellence(rateNum);
            }
        });
        //评分10：沸腾鱼
        ScaleRatingBar ratingBar10=(ScaleRatingBar)findViewById(R.id.simpleRatingBar10);
        ratingBar10.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                //rating就是点星星打的评分
                //rateFood[0].setExcellence(评分);    //满分10分
                int rateNum=(int)(rating*2);
                TextView tv_rating10=findViewById(R.id.tv_rating10);
                tv_rating10.setText("    你的评分是 "+rateNum+".");
                rateFood[9].setExcellence(rateNum);
            }
        });


        Button buttonRateSubmit=(Button)findViewById(R.id.bt_rateSubmit);
        buttonRateSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientRatefood.this,"评分提交成功！",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ClientRatefood.this,ClientActivity.class);
                startActivity(intent);

            }
        });

    }

    //******************************************
    //暂时先把excellence设成1了，评分的时候再改？
    //*****************************************
    void init(){
        rateFood[0] = new RecommendFood("麻辣烫",getRandomLengthName("麻辣烫"), R.drawable.malatang,10,1,9,0,0,6,4);

        rateFood[1] = new RecommendFood("糖醋里脊",getRandomLengthName("糖醋里脊"), R.drawable.tangculiji,5,1,0,7,4,1,4);

        rateFood[2] = new RecommendFood("酸辣土豆丝",getRandomLengthName("酸辣土豆丝"), R.drawable.suanlatudousi,2,1,3,1,7,6,3);

        rateFood[3] = new RecommendFood("牛肉酱拌面",getRandomLengthName("牛肉酱拌面"), R.drawable.niuroujiangbanmian,6,1,0,0,0,7,3);

        rateFood[4] = new RecommendFood("烤鸭饭",getRandomLengthName("烤鸭饭"), R.drawable.kaoyafan,9,1,0,1,1,4,9);

        rateFood[5] = new RecommendFood("羊肉小火锅",getRandomLengthName("羊肉小火锅"), R.drawable.yangrouxiaohuoguo,18,1,6,0,0,3,5);

        rateFood[6] = new RecommendFood("绿豆汤",getRandomLengthName("绿豆汤"), R.drawable.lvdoutang,3,1,0,2,0,0,0);

        rateFood[7] = new RecommendFood("烤冷面",getRandomLengthName("烤冷面"), R.drawable.kaolengmian,6,1,2,4,4,3,4);

        rateFood[8] = new RecommendFood("羊肉泡馍",getRandomLengthName("羊肉泡馍"), R.drawable.yangroupaomo,13,1,2,0,2,6,5);

        rateFood[9]= new RecommendFood("沸腾鱼",getRandomLengthName("沸腾鱼"), R.drawable.feitengyu,5,1,3,0,0,5,7);

    }
    private String getRandomLengthName(String name){    //这个到时候没用了可以删掉
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++)
            builder.append(name);
        return builder.toString();
    }

    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    OkHttpClient client=new OkHttpClient();
                    connection.setRequestMethod("POST");
                    Request request=new Request.Builder()
                            .url("http://10.0.2.2/HomePageImg.json")   //服务器的json地址，到时候改这里
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithGSON(responseData);        //用gson处理json数据
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){    //用gson处理json数据
        Gson gson=new Gson();
        List<RecommendFood> hImgList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
        {}.getType());
        int i=0;
        for(RecommendFood hImg:hImgList){
            //Log.d("MainActivity","id is "+app.getId());
            rateFood[i++]=hImg;
            //foodimages[i++]=hImg.getImageId();     //设置foodimages[]图像源
        }
    }
}
