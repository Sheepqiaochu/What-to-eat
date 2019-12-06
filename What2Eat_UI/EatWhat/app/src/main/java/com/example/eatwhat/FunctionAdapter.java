package com.example.eatwhat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FunctionAdapter extends ArrayAdapter<Function> {
    private int resourceId;

    public FunctionAdapter(Context context, int textViewResourceId, List<Function> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Function function = getItem(position);//获取当前Function实例
        //为子例加载传入的布局
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view=LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder =new ViewHolder();
            viewHolder.functionImage=(ImageView)view.findViewById(R.id.function_image);
            viewHolder.functionName=(TextView)view.findViewById(R.id.function_name);
            view.setTag(viewHolder);//将viewHolder存储在View中
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();//重新获取ViewHolder
        }



        viewHolder.functionImage.setImageResource(function.getImageId());
        viewHolder.functionName.setText(function.getName());
        return view;//返回布局
    }

    //用于对控件的实例进行缓存
    class ViewHolder {
        ImageView functionImage;
        TextView functionName;
    }


}
