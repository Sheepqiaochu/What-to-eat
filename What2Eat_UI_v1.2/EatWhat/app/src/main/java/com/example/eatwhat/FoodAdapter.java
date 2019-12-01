package com.example.eatwhat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<RecommendFood> mFoodList;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView foodImage;
        TextView foodName;

        public ViewHolder(View view) {
            super(view);
            fruitView=view;
            foodImage = (ImageView) view.findViewById(R.id.fooditem_image);
            foodName = (TextView) view.findViewById(R.id.fooditem_name);
        }

    }

    public FoodAdapter(Context context, List<RecommendFood> fruitList) {
        this.context=context;
        mFoodList = fruitList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {    //点击文字描述
            @Override
            public void onClick(View view) {        //点击事件，到时候改这里
                int position=holder.getAdapterPosition();
                RecommendFood food=mFoodList.get(position);
                /*Toast.makeText(view.getContext(),"you clicked view "+food.getName(),
                        Toast.LENGTH_SHORT).show();*/
                Intent intent=new Intent("com.example.eatwhat.ACTION_DETAIL");
                context.startActivity(intent);
            }
        });
        holder.foodImage.setOnClickListener(new View.OnClickListener() {    //点击图片
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                RecommendFood food=mFoodList.get(position);
                /*Toast.makeText(view.getContext(),"you clicked image "+food.getName(),
                        Toast.LENGTH_SHORT).show();*/
                Intent intent=new Intent("com.example.eatwhat.ACTION_DETAIL");
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecommendFood food = mFoodList.get(position);
        holder.foodImage.setImageResource(food.getImageId());
        holder.foodName.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }
}