package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientStars extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    public List<RecommendFood> foodStaredList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_stars);

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }



        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


            sendRequestWithOkHttp();    //通过okhttp传送请求


        synchronized (ClientStars.this) {
            try {
                ClientStars.this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(ClientStars.this,foodStaredList);
        recyclerView.setAdapter(adapter);

        ImageButton returnButton=(ImageButton)findViewById(R.id.button_return4);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加点击事件
                //Intent intent=new Intent(ClientStars.this,ClientActivity.class);
                //startActivity(intent);
                finish();
            }
        });
    }


    private void sendRequestWithOkHttp(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (ClientStars.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();

                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                .url("http://101.201.56.86:90/getFavorite?uid="+MyApplication.user.getUid())   //服务器的json地址，到时候改这里

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
        List<RecommendFood> rFoodList=gson.fromJson(jsonData,new TypeToken<List<RecommendFood>>()
            {}.getType());
        for(RecommendFood rFood:rFoodList){

            //RecommendFood mango = new RecommendFood(getRandomLengthName("Mango"), R.drawable.mango_pic);
            foodList.add(rFood);
        }*/


        try{
            //JSONObject jsonObject=new JSONObject(jsonData).getJSONObject("data");
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int n=0;n<jsonArray.length();n++){
                JSONObject jsonObject1=jsonArray.getJSONObject(n);

                RecommendFood recommendFood=new RecommendFood();
                recommendFood.setDid(jsonObject1.getInt("did"));
                recommendFood.setF1(jsonObject1.getInt("f1"));
                recommendFood.setF2(jsonObject1.getInt("f2"));
                recommendFood.setF3(jsonObject1.getInt("f3"));
                recommendFood.setF4(jsonObject1.getInt("f4"));
                recommendFood.setF5(jsonObject1.getInt("f5"));
                recommendFood.setImageId(R.drawable.mango_pic);
                recommendFood.setImageUrl(jsonObject1.getString("imagePath"));
                recommendFood.setName(jsonObject1.getString("name"));
                recommendFood.setPrice(jsonObject1.getDouble("price"));
                recommendFood.setDetail("所在学部："+jsonObject1.getString("raddress")+"\n具体地址："+jsonObject1.getString("rname"));
                recommendFood.setExcellence(jsonObject1.getInt("priority"));
                recommendFood.setFavorite(jsonObject1.getBoolean("favorite"));
                //if(MyApplication.foodList.size()<jsonArray.length())
                foodStaredList.add(recommendFood);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ClientStars.this.notifyAll();
    }
}
