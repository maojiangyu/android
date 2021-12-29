package com.example.travel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travel.DataBase.Logined;
import com.example.travel.DataBase.Scenery;
import com.example.travel.DataBase.SceneryChoosed;
import com.example.travel.HomePage.notifications.UploadActivity;
import com.example.travel.LoginPage.LoginActivity;
import com.example.travel.RegisterPage.RegisterActivity;

import java.io.ByteArrayOutputStream;

public class ClickedActivity extends AppCompatActivity {

    ImageView ivLike;
    ImageView ivStar;
    SceneryChoosed scd = new SceneryChoosed();
    Logined ld = new Logined();
    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel1.db";
    public static final String TABLE_SCENERY = "tbl_scenery";
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        ActionBar actionBar = getSupportActionBar();//添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        TextView tvn =(TextView)findViewById(R.id.SceneryName);
        this.setTitle(scd.getName());

        tvn.setText(scd.getName());
        TextView tvi =(TextView)findViewById(R.id.SceneryInfo);
        tvi.setText("    "+scd.getInfo());
        ImageView iv =(ImageView)findViewById(R.id.SceneryImg);
        iv.setImageBitmap(scd.getImg());

        ivLike =(ImageView)findViewById(R.id.ifLike);
        ivStar =(ImageView)findViewById(R.id.ifStar);

        updateLikeAndStar();

        ivLike.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       runDatabaseLike();
                       updateLikeAndStar();
                    }
                }
        );
        ivStar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runDatabaseStar();
                        updateLikeAndStar();
                    }
                }
        );

    }
    private void updateLikeAndStar() {
        if(ld.getIsLogin()==false){
            return;
        }else{
            if(scd.getClassify()==6&&ld.getIsLogin()){
                ivStar.setImageResource(R.drawable.ic_star);
                ivLike.setImageResource(R.drawable.ic_heart);
            }else{
                if(scd.getClassify()==4&&ld.getIsLogin()){
                    ivLike.setImageResource(R.drawable.ic_heart);
                }else{
                    ivLike.setImageResource(R.drawable.ic_blank_heart);
                }

                if(scd.getClassify()==5&&ld.getIsLogin()){
                    ivStar.setImageResource(R.drawable.ic_star);
                    ivLike.setImageResource(R.drawable.ic_heart);
                }else{
                    ivStar.setImageResource(R.drawable.ic_blank_star);
                }
            }
        }
    }
    @SuppressLint("WrongConstant")
    private void runDatabaseLike() {
        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        mDatabase.beginTransaction();
        int classifyNeedToChange=scd.getClassify();
        int likeNeedToChange=scd.getFavorite();
        if(scd.getClassify()!=6){
            if(scd.getClassify()>=4){
                classifyNeedToChange=3;
                likeNeedToChange--;
            }else {
                classifyNeedToChange=4;
                likeNeedToChange++;
            }
        }else {
            return;
        }
        scd.setClassify(classifyNeedToChange);
        scd.setFavorite(likeNeedToChange);
        try {
            ContentValues values=new ContentValues();
            values.put("favorite",likeNeedToChange);
            values.put("classify",classifyNeedToChange);
            String[] args = new String[]{String.valueOf(scd.getId())};
            mDatabase.update(TABLE_SCENERY, values,"id = ?",args);

            String sql = "select * from tbl_scenery where classify > ?";
            Cursor cursor = mDatabase.rawQuery(sql, new String[]{"3"});
            Log.d("我的数据库的长度", String.valueOf(cursor.getCount()));
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }
    @SuppressLint("WrongConstant")
    private void runDatabaseStar() {
        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        mDatabase.beginTransaction();
        int classifyNeedToChange=scd.getClassify();
        int likeNeedToChange=scd.getFavorite();
        if(scd.getClassify()!=6){
            if(scd.getClassify()==5){
                classifyNeedToChange=3;
                likeNeedToChange--;
            }else if(scd.getClassify()<=3){
                classifyNeedToChange=5;
                likeNeedToChange++;
            }else {
                classifyNeedToChange=5;
            }
        }else {
            return;
        }
        scd.setClassify(classifyNeedToChange);
        scd.setFavorite(likeNeedToChange);
        try {
            ContentValues values=new ContentValues();
            values.put("favorite",likeNeedToChange);
            values.put("classify",classifyNeedToChange);
            String[] args = new String[]{scd.getName()};
            mDatabase.update(TABLE_SCENERY, values,"name = ?",args);

            String sql = "select * from tbl_scenery where classify > ?";
            Cursor cursor = mDatabase.rawQuery(sql, new String[]{"3"});
            Log.d("我的数据库的长度", String.valueOf(cursor.getCount()));
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {//重写onOptionsItemSelected（添加返回按钮 ）
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}