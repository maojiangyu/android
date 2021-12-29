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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel.DataBase.Logined;
import com.example.travel.R;

import java.io.ByteArrayOutputStream;

public class SettingActivity extends AppCompatActivity {

    private static final int REQUEST_ID_IMAGE_CHOOSE = 99;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;

    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel1.db";
    private static final String TABLE_LOGIN = "tbl_login";
    private SQLiteDatabase mDatabase;

    private Bitmap temporary;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.setTitle("编辑资料");
        ActionBar actionBar = getSupportActionBar();//添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Logined ld = new Logined();
        iv = (ImageView)findViewById(R.id.headImg);
        iv.setImageBitmap(ld.getImg());

        Button btn_photo= (Button)findViewById(R.id.photo);
        Button btn_album= (Button)findViewById(R.id.album);
        Button btn_save= (Button)findViewById(R.id.save);
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
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();showNormalDialog();
            }
        });
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
    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SettingActivity.this);
        normalDialog.setTitle("修改信息");
        normalDialog.setMessage("保存成功");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }
    public boolean onOptionsItemSelected(MenuItem item) {//重写onOptionsItemSelected（添加返回按钮 ）
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//调用完相机并拍照后会执行
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                temporary= createCircleBitmap(bitmap);
                this.iv.setImageBitmap(temporary);
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
                    temporary= createCircleBitmap(bitmap);
                    this.iv.setImageBitmap(temporary);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap createCircleBitmap(Bitmap resource){//把图片裁剪成圆形
        int width = resource.getWidth();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap circleBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(width/2, width/2, width/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(resource, 0, 0, paint);
        return circleBitmap;
    }
    @SuppressLint("WrongConstant")
    private void save() {//保存
        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        final TextView pwd = (TextView)findViewById(R.id.input1);
        final TextView sign = (TextView)findViewById(R.id.input2);
        final String newPwd = pwd.getText().toString();
        final String newSign = sign.getText().toString();
        Logined ld = new Logined();
        if(!pwd.equals("")){ld.setPwd(newPwd);}
        if(!sign.equals("")){ld.setSign(newSign);}
        mDatabase.beginTransaction();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            temporary.compress(Bitmap.CompressFormat.PNG, 20, os);

            ld.setImg(temporary);
            ContentValues values=new ContentValues();
            values.put("password",ld.getPwd());
            values.put("sign",ld.getSign());
            values.put("headImg", os.toByteArray());

            String[] args = new String[]{ld.getName()};
            mDatabase.update(TABLE_LOGIN, values,"username = ?",args);

            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }

    }
}