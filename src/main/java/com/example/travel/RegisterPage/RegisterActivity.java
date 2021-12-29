package com.example.travel.RegisterPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travel.DataBase.User;
import com.example.travel.LoginPage.LoginActivity;
import com.example.travel.R;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel.db";
    public static final String TABLE_LOGIN = "tbl_login";
    private SQLiteDatabase mDatabase;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("注册");

        ActionBar actionBar = getSupportActionBar();//添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button btn_register= (Button)findViewById(R.id.RegisterButton);
        Button btn_back= (Button)findViewById(R.id.BackButton);
        btn_register.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(View v) {
                        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                                SQLiteDatabase.CREATE_IF_NECESSARY, null);
                        addSomeID();
                    }
                }
        );
        btn_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
        );

    }
    public boolean onOptionsItemSelected(MenuItem item) {//重写onOptionsItemSelected（添加返回按钮 ）
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void addSomeID() {
        Log.i(DEBUG_TAG, "Database Transaction Start");
        mDatabase.beginTransaction();
        try {
            Log.i(DEBUG_TAG, "Database Transaction?  "
                    + mDatabase.inTransaction());
            Log.i(DEBUG_TAG, "Database Locked by current thread?  "
                    + mDatabase.isDbLockedByCurrentThread());

            final TextView struserid = (TextView)findViewById(R.id.RegisterInput1);
            final TextView strpassword = (TextView)findViewById(R.id.RegisterInput2);
            final String userId = struserid.getText().toString();
            final String password = strpassword.getText().toString();
            if(userId.equals("")||password.equals("")){
                showNormalDialog();
                return ;
            }
            int id = (int)(Math.floor(Math.random()*1000000));
            String str = String.valueOf(id);
            if(str.length()<6){
                for(int i=0;i<6-str.length();i++)
                    str="0"+str;
            }
            addID(new User(userId,password,str,0,"这个人懒死了什么都没有写"));//新用户信息
            mDatabase.setTransactionSuccessful();
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
        Log.i(DEBUG_TAG, "Database Transaction End");
    }
    private void showNormalDialog(){//显示对话框
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegisterActivity.this);
        normalDialog.setTitle("注册失败");
        normalDialog.setMessage("密码或用户名不能为空");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }
    private void addID(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.name);
        values.put("password", user.pwd);
        values.put("programId", user.programId);
        values.put("level", user.level);
        values.put("sign", user.sign);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.people);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        values.put("headImg", os.toByteArray());

        user.id = mDatabase.insertOrThrow(TABLE_LOGIN, null, values);
        Log.i(DEBUG_TAG, "Added user");
    }
}