package com.example.travel.HomePage.notifications;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travel.DataBase.Logined;
import com.example.travel.DataBase.Scenery;
import com.example.travel.MainActivity;
import com.example.travel.R;

import java.io.ByteArrayOutputStream;

public class UploadActivity extends AppCompatActivity {
    private static final int REQUEST_ID_IMAGE_CHOOSE = 109;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 110;

    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel1.db";
    public static final String TABLE_SCENERY = "tbl_scenery";
    private SQLiteDatabase mDatabase;

    private ImageView iv;
    private Bitmap temporary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        this.setTitle("上传动态");
        ActionBar actionBar = getSupportActionBar();//添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Scenery sy = new Scenery();

        final Button upload = (Button)findViewById(R.id.upload);


        Button btn_photo= (Button)findViewById(R.id.photoUpload);
        Button btn_album= (Button)findViewById(R.id.albumUpload);

        iv = (ImageView)findViewById(R.id.uploadImg);
        iv.setImageResource(R.drawable.plus);

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        upload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upload();
                    }
                }
        );
    }
    @SuppressLint("WrongConstant")
    private void upload(){
        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        final EditText titleET = (EditText)findViewById(R.id.inputTiTle);
        final EditText infoET = (EditText)findViewById(R.id.inPutInfo);
        final String title = titleET.getText().toString();
        final String info = infoET.getText().toString();
        if(title.equals("")||info.equals("")){
            showNormalDialog(2);
            return ;
        }
        mDatabase.beginTransaction();
        try {
            if(temporary==null){
                temporary= BitmapFactory.decodeResource(getResources(), R.drawable.image4);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            temporary.compress(Bitmap.CompressFormat.PNG, 40, os);


            Logined ld =new Logined();
            ContentValues values=new ContentValues();
            values.put("name", title);
            values.put("info", info);
            values.put("username", ld.getName());
            values.put("favorite", 0);
            values.put("classify", 6);

            values.put("sceneryImg", os.toByteArray());

            mDatabase.insertOrThrow(TABLE_SCENERY, null, values);

            mDatabase.setTransactionSuccessful();

            showNormalDialog(1);
            Log.i(DEBUG_TAG,"success upload");
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }
    private void showNormalDialog(int jud){//上传成功
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(UploadActivity.this);
        if(jud ==1){
            normalDialog.setTitle("上传");
            normalDialog.setMessage("上传成功");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(UploadActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
        } else{
            normalDialog.setTitle("上传");
            normalDialog.setMessage("上传失败,内容不能为空");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }

        normalDialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//调用完相机并拍照后会执行
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                temporary = bitmap;
                this.iv.setImageBitmap(bitmap);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_ID_IMAGE_CHOOSE) {
            if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                try {
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    temporary = bitmap;
                    this.iv.setImageBitmap(temporary);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
    }
    private void chooseImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CHOOSE);
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