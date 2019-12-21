package com.example.eatwhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.yinglan.shadowimageview.ShadowImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.json.JSONArray;
import org.json.JSONObject;

import ezy.ui.view.RoundButton;

import static com.example.eatwhat.Login.JSON;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //private ShadowImageView imageView;
    private ImageView imageView;
    private TextView textView;
    private int foodNum=5;    //推荐的食物数量
    private RecommendFood[] recommendFood=new RecommendFood[foodNum];//假装推荐5个
    private RecommendFood currentFood;  //当前显示的食物
    private double currentLike;   //当前显示的食物点赞8分点踩2分
    /*private int[] foodimages = new int[]{   //食物图片，暂时是从文件夹里弄来的
            R.drawable.apple_pic,
            R.drawable.banana_pic,
            R.drawable.cherry_pic
    };*/
    private int refreshtime=0;  //刷新次数
    public int numLike=0;//这个是like按钮被点击的次数，0=未选中，1=喜欢，-1=不喜欢
    //int numDislike=0;
    ShineButton likeButton;
    ShineButton dislikeButton;

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar=getSupportActionBar();  //隐藏原来的标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        //下面三行准备删掉
        recommendFood[0]=new RecommendFood("登录获得推荐菜",getRandomLengthName("Apple"), R.drawable.apple_pic,10,1,1.3,1.3,1.3,1.3,1.3);           //临时初始化，到时候删掉
        recommendFood[1]=new RecommendFood("登录获得推荐菜",getRandomLengthName("Banana"), R.drawable.banana_pic,8,2,1.6,2.9,6.6,1.3,1.3);
        recommendFood[2]=new RecommendFood("登录获得推荐菜",getRandomLengthName("Orange"), R.drawable.orange_pic,6,3,3.9,4.4,8.7,1.3,1.3);
        recommendFood[3]=new RecommendFood("登录获得推荐菜",getRandomLengthName("Banana"), R.drawable.banana_pic,8,2,1.6,2.9,6.6,1.3,1.3);
        recommendFood[4]=new RecommendFood("登录获得推荐菜",getRandomLengthName("Orange"), R.drawable.orange_pic,6,3,3.9,4.4,8.7,1.3,1.3);

        for(int i=0;i<5;i++)
            recommendFood[i].setImageUrl("http://101.201.56.86:90/images/1.jpg");

        //recommendFood[0]=new RecommendFood();
        sendRequestWithOkHttp();    //通过okhttp传送请求

        if(MyApplication.user.getUid()!=-1)
            synchronized (MainActivity.this) {
                try {
                    MainActivity.this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        currentFood=recommendFood[0];

        RoundButton button=(RoundButton)findViewById(R.id.button_refresh) ;
        //Button button=(Button)findViewById(R.id.button_refresh);
        //修改的图片部分
        //***********************
        //imageView=(ImageView)findViewById(R.id.image_food);
        imageView=(ImageView)findViewById(R.id.image_food);


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        boolean picOk=false;
        try{
            Bitmap bitmap=getBitmap(currentFood.getImageUrl());
            imageView.setImageBitmap(bitmap);
            picOk=true;
        }catch(IOException e){
            e.printStackTrace();
        }

        if(picOk==false)
            imageView.setImageResource(currentFood.getImageId());


        imageView.setOnClickListener(this);
        textView=(TextView)findViewById(R.id.tv_foodname);
        textView.setText(currentFood.getName());
        textView.setOnClickListener(this);
        button.setOnClickListener(this);


        TextView textview = (TextView) findViewById(R.id.top_menu);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);

        //like & dislike
        //******************************************************
        //从这里可以直接修改推荐度还是？？？？
        //******************************************************

        likeButton=findViewById(R.id.bt_like);
        dislikeButton=findViewById(R.id.bt_dislike);
        //修改ShineButton的底图
        dislikeButton.setShapeResource(R.drawable.dislike);

        //设置两个按钮的点击事件
        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);





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
                numLike=0;
                //likeButton.变成灰色();
                ///dislikeButton.变成灰色();
                likeButton.setChecked(false);
                dislikeButton.setChecked(false);
                likeButton.setEnabled(true);
                dislikeButton.setEnabled(true);
                refreshtime=(refreshtime+1)%foodNum;
                currentFood=recommendFood[refreshtime];

                boolean picOk=false;
                try{
                    Bitmap bitmap=getBitmap(currentFood.getImageUrl());
                    imageView.setImageBitmap(bitmap);
                    picOk=true;
                }catch(IOException e){
                    e.printStackTrace();
                }

                if(picOk==false)
                    imageView.setImageResource(recommendFood[refreshtime].getImageId());


                textView.setText(recommendFood[refreshtime].getName());
                break;
            case R.id.bt_like:      //点击like按钮
                //调用setExcellence函数改变正在推荐的食物的推荐度
                //*********************************************

                //*********************************************
                numLike=numLike==0?1:0;
                //使只能单选
                if(numLike==1){    //like被选中
                    dislikeButton.setEnabled(false);
                    currentLike=8;
                    sendRequestWithOkHttp_2();
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sendRequestWithOkHttp();    //通过okhttp传送请求
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{//like取消选中
                    dislikeButton.setEnabled(true);
                    currentLike=currentFood.getExcellence();
                    sendRequestWithOkHttp_2();
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.bt_dislike:   //点击dislike按钮
                //调用setExcellence函数改变正在推荐的食物的推荐度
                //*********************************************
                //currentLike=0;
                //sendRequestWithOkHttp_2();
                //*********************************************
                numLike=numLike==0?-1:0;
                //使只能单选
                if(numLike==-1){    //dislike被选中
                    likeButton.setEnabled(false);
                    currentLike=0;
                    sendRequestWithOkHttp_2();
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sendRequestWithOkHttp();    //通过okhttp传送请求
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{//dislike取消选中
                    likeButton.setEnabled(true);
                    currentLike=currentFood.getExcellence();
                    sendRequestWithOkHttp_2();
                    synchronized (MainActivity.this) {
                        try {
                            MainActivity.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.image_food:
            case R.id.tv_foodname:
                Intent intent=new Intent(MainActivity.this,RecommendDetail.class);
                intent.putExtra("food",currentFood);   //传参
                intent.putExtra("page",0);
                startActivity(intent);
                break;
            default:
                break;
        }
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
                synchronized (MainActivity.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();
                        //connection.setRequestMethod("POST");
                    /*Map map=new HashMap<>();
                    map.put("uid",ClientActivity.user.getUid());
                    String param=new Gson().toJson(map);
                    RequestBody body=RequestBody.create(JSON,param);*/
                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                //.url("http://101.201.56.86:90/getAllDishes")
                                .url("http://101.201.56.86:90/getRecommend?uid="+MyApplication.user.getUid())   //服务器的json地址，到时候改这里
                                //.post(body)
                                .build();

                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        parseJSONWithGSON(responseData);        //用gson处理json数据
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }}
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){    //用gson处理json数据
        /*Gson gson=new Gson();
        List<RecommendFood> hImgList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
            {}.getType());
        int i=0;
        for(RecommendFood hImg:hImgList){
            //Log.d("MainActivity","id is "+app.getId());
            recommendFood[i++]=hImg;
            //foodimages[i++]=hImg.getImageId();     //设置foodimages[]图像源
        }*/


        try{
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int n=0;n<foodNum;n++){
                JSONObject jsonObject1=jsonArray.getJSONObject(n);
                RecommendFood rFood=new RecommendFood();
                rFood.setDid(jsonObject1.getInt("did"));
                rFood.setF1(jsonObject1.getInt("f1"));
                rFood.setF2(jsonObject1.getInt("f2"));
                rFood.setF3(jsonObject1.getInt("f3"));
                rFood.setF4(jsonObject1.getInt("f4"));
                rFood.setF5(jsonObject1.getInt("f5"));
                rFood.setImageUrl(jsonObject1.getString("imagePath"));

                rFood.setImageId(R.drawable.apple_pic);

                rFood.setName(jsonObject1.getString("name"));
                rFood.setPrice(jsonObject1.getDouble("price"));
                rFood.setDetail("所在学部："+jsonObject1.getString("raddress")+"\n具体地址："+jsonObject1.getString("rname"));
                rFood.setExcellence(n+1);
                rFood.setFavorite(jsonObject1.getBoolean("favorite"));
                recommendFood[n]=rFood;
                //foodList.add(recommendFood);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        MainActivity.this.notifyAll();
    }

    private void sendRequestWithOkHttp_2(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (MainActivity.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();
                        //connection.setRequestMethod("POST");
                        Map map=new HashMap<>();
                        map.put("uid",MyApplication.user.getUid());
                        int[] did= new int[1];
                        did[0]=currentFood.getDid();
                        map.put("did",did);
                        double[] point= new double[1];
                        point[0]=currentLike;
                        map.put("point",point);
                        String param=new Gson().toJson(map);
                        RequestBody body=RequestBody.create(JSON,param);
                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                //.url("http://101.201.56.86:90/getAllDishes")
                                .url("http://101.201.56.86:90/initEvaluate")   //服务器的json地址，到时候改这里
                                .post(body)
                                .build();

                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        int t=0;
                        //parseJSONWithGSON(responseData);        //用gson处理json数据
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    MainActivity.this.notifyAll();
                }}
        }).start();
    }

    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
