package com.example.eatwhat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{

    private List<RecommendFood> mFoodList_backup;
    private List<RecommendFood> mFoodList_show;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {   //ViewHolder
        CardView foodView;
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;
        TextView foodExcellence;

        public ViewHolder(View view) {
            super(view);
            foodView=(CardView) view;
            foodImage = (ImageView) view.findViewById(R.id.fooditem_image);
            foodName = (TextView) view.findViewById(R.id.fooditem_name);
            foodPrice=(TextView) view.findViewById(R.id.fooditem_price);
            foodExcellence=(TextView) view.findViewById(R.id.fooditem_excellence);
        }

    }

    public FoodAdapter(Context context, List<RecommendFood> fruitList) {    //FoodAdapter
        this.context=context;
        mFoodList_show = fruitList;
        mFoodList_backup=new ArrayList<>();
        mFoodList_backup.addAll(fruitList);
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.foodView.setOnClickListener(new View.OnClickListener() {    //点击文字描述
            @Override
            public void onClick(View view) {        //点击事件，到时候改这里

                int position=holder.getAdapterPosition();
                RecommendFood food=mFoodList_show.get(position);
                /*Toast.makeText(view.getContext(),"you clicked view "+food.getName(),
                        Toast.LENGTH_SHORT).show();*/
                Intent intent=new Intent("com.example.eatwhat.ACTION_DETAIL");
                intent.putExtra("food",food);   //传参
                intent.putExtra("page",1);   //传参
                context.startActivity(intent);
            }
        });
        /*holder.foodImage.setOnClickListener(new View.OnClickListener() {    //点击图片
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                RecommendFood food=mFoodList_show.get(position);
                /*Toast.makeText(view.getContext(),"you clicked image "+food.getName(),
                        Toast.LENGTH_SHORT).show();*/
        /*        Intent intent=new Intent("com.example.eatwhat.ACTION_DETAIL");
                intent.putExtra("food",food);   //传参
                context.startActivity(intent);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecommendFood food = mFoodList_show.get(position);
        holder.foodImage.setImageResource(food.getImageId());
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText("$"+food.getPrice());
        holder.foodExcellence.setText(food.getExcellence()+"%");
    }

    @Override
    public int getItemCount() {
        return mFoodList_show.size();
    }


    public void setmFoodList_show(String searchtext){   //选择搜索到的条目显示
        mFoodList_show.clear();
        for (RecommendFood food:mFoodList_backup){
            if (food.getName().contains(searchtext))
                mFoodList_show.add(food);
        }
    }

    public void setSelectedFoodList_show(double f1,double f2,double f3){   //选择筛选到的条目显示
        mFoodList_show.clear();
        for (RecommendFood food:mFoodList_backup){
            if (food.getF1()>=f1&&food.getF1()<=(f1+2.5)&&
                    food.getF2()>=f2&&food.getF2()<=(f2+2.5)&&
                    food.getF3()>=f3&&food.getF3()<=(f3+2.5))
                mFoodList_show.add(food);
        }
    }
}