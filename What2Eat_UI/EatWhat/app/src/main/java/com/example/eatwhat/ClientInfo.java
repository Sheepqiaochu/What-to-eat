package com.example.eatwhat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//账号管理
public class ClientInfo extends AppCompatActivity {

    private String newUserphone;
    private String newUsername;
    EditText et_newUserphone;
    EditText getEt_newUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        et_newUserphone=(EditText)findViewById(R.id.et_new_phone);
        getEt_newUsername=(EditText)findViewById(R.id.et_new_username);

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        ImageButton buttonReturn=findViewById(R.id.button_return3);

        //点击返回
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到“我的”的页面
                Intent intent=new Intent(ClientInfo.this,ClientActivity.class);
                startActivity(intent);
            }
        });

        //点击提交确认

        Button buttonChange=(Button)findViewById(R.id.bt_change_submit);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();

                if(!TextUtils.isEmpty(newUserphone)) {
                    Toast.makeText(ClientInfo.this,"修改了用户名(手机号)",Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(newUsername)) {
                    Toast.makeText(ClientInfo.this,"修改了用户昵称",Toast.LENGTH_SHORT).show();
                }
                //**************************************
                //注：并没有把值传过去
                //**************************************

                //跳转到“我的”页面
                Intent intent=new Intent(ClientInfo.this,ClientActivity.class);
                startActivity(intent);

            }
        });



    }

    private void getEditString()
    {
        newUserphone=et_newUserphone.getText().toString().trim();
        newUsername=getEt_newUsername.getText().toString().trim();
    }

}
