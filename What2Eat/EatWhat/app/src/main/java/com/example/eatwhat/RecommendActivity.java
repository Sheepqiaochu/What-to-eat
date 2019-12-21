package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.json.JSONArray;
import org.json.JSONObject;

import ezy.ui.view.RoundButton;

import static com.example.eatwhat.Login.JSON;

public class RecommendActivity extends AppCompatActivity implements View.OnClickListener {

    private List<RecommendFood> foodList = new ArrayList<>();
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private boolean priceascend=true;
    private boolean excellencedescend=true;
    private double f1;   //菜的特征
    private double f2;
    private double f3;
    private double f4;
    private double f5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ActionBar actionbar=getSupportActionBar();  //隐藏默认标题栏
        if(actionbar!=null){
            actionbar.hide();
        }

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        if(MyApplication.user.getUid()==-1)    //未登录
            sendRequestWithOkHttp();    //通过okhttp传送请求
        else    //已登录
            sendRequestWithOkHttp_2();


        synchronized (RecommendActivity.this) {
            try {
                RecommendActivity.this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //initFoods();   //初始化食物们
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //StaggeredGridLayoutManager layoutManager=
        //        new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(RecommendActivity.this,foodList);
        recyclerView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.searchView);   //搜索
        searchView.setQuery("",false);
        // 设置搜索文本监听
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    // 当点击搜索按钮时触发该方法
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    // 当搜索内容改变时触发该方法
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //if (TextUtils.isEmpty(newText)) {
                            adapter.setmFoodList_show(newText);
                            adapter.notifyDataSetChanged();
                        //} else {
                          //  adapter.setmFoodList_show(newText);
                            //adapter.notifyDataSetChanged();
                        //}
                        return true;
                    }
                });

        Button sortbutton1=(Button)findViewById(R.id.sort_by_excellence);    //排序按钮
        sortbutton1.setOnClickListener(this);
        Button sortbutton2=(Button)findViewById(R.id.sort_by_price);
        sortbutton2.setOnClickListener(this);

        //*********************************
        //修改之后的另一种的spinner
        //辣！
        NiceSpinner sp_spicy=(NiceSpinner) findViewById(R.id.sp_spicy);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("不辣", "微辣","中辣🌶","狠辣🌶🌶","随意"));//给spinner传选项
        sp_spicy.attachDataSource(dataset1);
        //甜！
        NiceSpinner sp_sweet=(NiceSpinner) findViewById(R.id.sp_sweet);
        List<String> dataset2 = new LinkedList<>(Arrays.asList("不甜", "微甜", "中甜🍬","狠甜🍬🍬","随意"));//给spinner传选项
        sp_sweet.attachDataSource(dataset2);
        //酸
        NiceSpinner sp_acid=(NiceSpinner) findViewById(R.id.sp_acid);//不要在乎这个翻译
        List<String> dataset3 = new LinkedList<>(Arrays.asList("不酸", "微酸", "中酸🍋","狠酸🍋🍋","随意"));//给spinner传选项
        sp_acid.attachDataSource(dataset3);
        //咸！
        NiceSpinner sp_salty=(NiceSpinner) findViewById(R.id.sp_salty);
        List<String> dataset4 = new LinkedList<>(Arrays.asList("不咸", "微咸", "中咸🥫","狠咸🥫🥫","随意"));//给spinner传选项
        sp_salty.attachDataSource(dataset4);
        //油
        NiceSpinner sp_oil=(NiceSpinner) findViewById(R.id.sp_oil);
        List<String> dataset5 = new LinkedList<>(Arrays.asList("不油", "微油", "中油🍗","狠油🍗🍗","随意"));//给spinner传选项
        sp_oil.attachDataSource(dataset5);

        sp_spicy.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                f1=2.5*position;
                Toast.makeText(RecommendActivity.this, "选择了 " + item, Toast.LENGTH_SHORT).show();
            }
        });
        sp_sweet.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                f2=2.5*position;
                Toast.makeText(RecommendActivity.this, "选择了 " + item, Toast.LENGTH_SHORT).show();
            }
        });
        sp_acid.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                f3=2.5*position;
                Toast.makeText(RecommendActivity.this, "选择了 " + item, Toast.LENGTH_SHORT).show();
            }
        });
        sp_salty.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                f4=2.5*position;
                Toast.makeText(RecommendActivity.this, "选择了 " + item, Toast.LENGTH_SHORT).show();
            }
        });
        sp_oil.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                f5=2.5*position;
                Toast.makeText(RecommendActivity.this, "选择了 " + item, Toast.LENGTH_SHORT).show();
            }
        });


        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);   //筛选
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {     //这一大段用来解决点击穿透
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }
            @Override
            public void onDrawerClosed(View drawerView) {

            }
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //LinearLayout spinnertv=(LinearLayout)findViewById(R.id.spinner_tv); //点击筛选
        RelativeLayout spinnertv=(RelativeLayout) findViewById(R.id.spinner_tv);
        spinnertv.setOnClickListener(this);
        //Button confirmbutton=(Button)findViewById(R.id.confirm_select); //确认筛选按钮
        RoundButton confirmbutton=(RoundButton)findViewById(R.id.confirm_select) ;
        confirmbutton.setOnClickListener(this);

        ActivityCollector.addActivity(this);    //加入活动到活动收集器中

        /*
        TextView textview = (TextView) findViewById(R.id.top_recommend);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/font_main#1.ttf");
        textview.setTypeface(typeface);
*/
        /*RadioButton rb;
        rb=findViewById(R.id.rb_home);
        rb.setChecked(true);*/
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
            case R.id.sort_by_price:   //按价格排序
                Collections.sort(foodList, new Comparator<RecommendFood>() {
                    @Override
                    public int compare(RecommendFood r1, RecommendFood r2) {
                        double diff;
                        Button button=findViewById(R.id.sort_by_price);
                        if(priceascend==true) {   //升序
                            diff = r1.getPrice() - r2.getPrice();
                            button.setText("按价格降序");
                        }
                        else{   //降序
                            diff = r2.getPrice() - r1.getPrice();
                            button.setText("按价格升序");
                        }
                        if (diff > 0) {
                            return 1;
                        }else if (diff < 0) {
                            return -1;
                        }
                        return 0;
                    }
                });
                priceascend=!priceascend;
                adapter.notifyDataSetChanged();
                break;
            case R.id.sort_by_excellence:   //按推荐度排序
                if(MyApplication.user.getUid()==-1){
                    Toast.makeText(RecommendActivity.this, "登录以使用该功能", Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.sort(foodList, new Comparator<RecommendFood>() {
                        @Override
                        public int compare(RecommendFood r1, RecommendFood r2) {
                            int diff;
                            Button button = findViewById(R.id.sort_by_excellence);
                            if (excellencedescend == false) {   //升序
                                diff = r2.getExcellence() - r1.getExcellence();
                                button.setText("按推荐度降序");
                            } else {   //降序
                                diff = r1.getExcellence() - r2.getExcellence();
                                button.setText("按推荐度升序");
                            }
                            if (diff > 0) {
                                return 1;
                            } else if (diff < 0) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    excellencedescend = !excellencedescend;
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.spinner_tv:       //筛选
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.confirm_select:   //确认筛选

                int featurenum=5;   //特征类型数


                NiceSpinner[] spinnerGroup=new NiceSpinner[featurenum];
                spinnerGroup[0]=(NiceSpinner) findViewById(R.id.sp_spicy);
                spinnerGroup[1]=(NiceSpinner) findViewById(R.id.sp_sweet);
                spinnerGroup[2]=(NiceSpinner) findViewById(R.id.sp_acid);
                spinnerGroup[3]=(NiceSpinner) findViewById(R.id.sp_salty);
                spinnerGroup[4]=(NiceSpinner) findViewById(R.id.sp_oil);

                String[] strings=new String[featurenum];
                for(int j=0;j<featurenum;j++) {
                    strings[j]= (String) spinnerGroup[j].getItemAtPosition(spinnerGroup[j].getSelectedIndex());
                }
                Toast.makeText(getApplicationContext(),"你选择了 <"+strings[0]+" "+strings[1]+" "+strings[2]
                        +" "+strings[3]+" "+strings[4]+">！", Toast.LENGTH_SHORT).show();

                adapter.setSelectedFoodList_show(f1,f2,f3,f4,f5); //筛选
                adapter.notifyDataSetChanged();

                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            default:
                break;
        }
    }

  /*  private void initFoods() {         //初始化食物们，到时候没用了可以删掉
        //for (int i = 0; i < 2; i++) {

            RecommendFood apple = new RecommendFood("apple",getRandomLengthName("Apple"), R.drawable.apple_pic,10,1,1.3,1.3,1.3,1.3,1.3);
            MyApplication.foodList.add(apple);
            RecommendFood banana = new RecommendFood("banana",getRandomLengthName("Banana"), R.drawable.banana_pic,8,2,1.6,2.9,6.6,8.8,8.6);
            MyApplication.foodList.add(banana);
            RecommendFood orange = new RecommendFood("orange",getRandomLengthName("Orange"), R.drawable.orange_pic,6,3,3.9,4.4,8.7,5.5,5.5);
            MyApplication.foodList.add(orange);
            RecommendFood watermelon = new RecommendFood("watermelon",getRandomLengthName("Watermelon"), R.drawable.watermelon_pic,11,4,0.8,0.9,7.7,3.7,2.9);
            MyApplication.foodList.add(watermelon);
            RecommendFood pear = new RecommendFood("pear",getRandomLengthName("Pear"), R.drawable.pear_pic,9,5,1.3,1.3,1.3,1.3,1.3);
            MyApplication.foodList.add(pear);
            RecommendFood grape = new RecommendFood("grape",getRandomLengthName("Grape"), R.drawable.grape_pic,12,6,1.3,1.3,1.3,1.3,1.3);
            MyApplication.foodList.add(grape);
            RecommendFood pineapple = new RecommendFood("pineapple",getRandomLengthName("Pineapple"), R.drawable.pineapple_pic,6,7,1.6,2.9,6.6,1.3,9.6);
            MyApplication.foodList.add(pineapple);
            RecommendFood strawberry = new RecommendFood("strawberry",getRandomLengthName("Strawberry"), R.drawable.strawberry_pic,8,8,1.6,2.9,6.6,1.3,9.6);
            MyApplication.foodList.add(strawberry);
            RecommendFood cherry = new RecommendFood("cherry",getRandomLengthName("Cherry"), R.drawable.cherry_pic,13,9,0.8,0.9,7.7,4.3,2.1);
            MyApplication.foodList.add(cherry);
            RecommendFood mango = new RecommendFood("mango",getRandomLengthName("Mango"), R.drawable.mango_pic,5,10,0.8,0.9,7.7,4.3,2.1);
            MyApplication.foodList.add(mango);
       // }
    }*/

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
                synchronized (RecommendActivity.this){
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    OkHttpClient client=new OkHttpClient();

                    //connection.setRequestMethod("POST");
                    Request request=new Request.Builder()
                            .url("http://101.201.56.86:90/getAllDishes")   //服务器的json地址，到时候改这里

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
                recommendFood.setExcellence(n);
                //if(MyApplication.foodList.size()<jsonArray.length())
                    foodList.add(recommendFood);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        RecommendActivity.this.notifyAll();
    }


    private void sendRequestWithOkHttp_2(){       //通过okhttp传送请求
        new Thread(new Runnable(){
            public void run(){
                synchronized (RecommendActivity.this){
                    HttpURLConnection connection=null;
                    BufferedReader reader=null;
                    try{
                        OkHttpClient client=new OkHttpClient();

                        /*Map map=new HashMap<>();
                        map.put("uid",MyApplication.user.getUid());
                        String param=new Gson().toJson(map);
                        RequestBody body=RequestBody.create(JSON,param);*/
                        //connection.setRequestMethod("POST");
                        Request request=new Request.Builder()
                                .url("http://101.201.56.86:90/getAllDishes?uid="+MyApplication.user.getUid())   //服务器的json地址，到时候改这里
                                //.post(body)
                                .build();
                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        parseJSONWithJSON(responseData);        //用json处理json数据
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }}
        }).start();
    }

    private void parseJSONWithJSON(String jsonData){    //用json处理json数据
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
                    foodList.add(recommendFood);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        RecommendActivity.this.notifyAll();
    }
}
