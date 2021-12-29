package com.example.travel.LoginPage;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.travel.DataBase.Logined;
import com.example.travel.DataBase.Scenery;
import com.example.travel.DataBase.User;
import com.example.travel.MainActivity;
import com.example.travel.R;
import com.example.travel.RegisterPage.RegisterActivity;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;


public class LoginActivity extends Activity {
    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel1.db";
    public static final String TABLE_LOGIN = "tbl_login";
    public static final String TABLE_SCENERY = "tbl_scenery";
    public static final String CREATE_LOGIN_TABLE = "CREATE TABLE tbl_login (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "username varchar(20),password varchar(20),programId varchar(20), level int(20),sign varchar(100),headImg BLOB);";
    public static final String CREATE_SCENERY_TABLE = "CREATE TABLE tbl_scenery (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "name varchar(20),info varchar(200),username varchar(20),favorite int(20), classify int(20),sceneryImg BLOB);";
    private SQLiteDatabase mDatabase;
    int upup = 0;//没啥用添加数据
    boolean canSee = false;
    ImageView iv;//密码可见设置


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("登入");

        TextView tvOne = findViewById(R.id.LoginText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "08华康娃娃体W5.TTF");
        tvOne.setTypeface(typeface);

        runDatabaseLogin();

        Button btn_login = (Button)findViewById(R.id.login_btn);
        Button btn_resign = (Button)findViewById(R.id.btn_register);
        Button btn_skip = (Button)findViewById(R.id.btn_skip);
        final EditText strUserId = (EditText)findViewById(R.id.LoginInput1);
        final EditText strPassword = (EditText)findViewById(R.id.LoginInput2);

        //密码是否可见
        iv = (ImageView)findViewById(R.id.show_password);
        iv.setImageResource(R.drawable.ic_show);
        iv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (canSee==false){
                            //如果是不能看到密码的情况下，
                            strPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            strPassword.setSelection(strPassword.getText().toString().length());
                            canSee=true;
                            iv.setImageResource(R.drawable.ic_hidden);

                        }else {
                            //如果是能看到密码的状态下
                            strPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            strPassword.setSelection(strPassword.getText().toString().length());
                            canSee=false;
                            iv.setImageResource(R.drawable.ic_show);
                        }
                    }
                }
        );
        //登入按钮
        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(View v) {
                        final String userId = strUserId.getText().toString();
                        final String password = strPassword.getText().toString();
                        String sql = "select * from tbl_login where username=? and password=?";
                        Cursor cursor = mDatabase.rawQuery(sql, new String[]{userId, password});
                        if (cursor.getCount() != 0) {
                            showNormalDialog(1);
                            Logined ld = new Logined();
                            cursor.moveToFirst();
                            ld.setId(cursor.getLong(0));
                            ld.setName(cursor.getString(1));
                            ld.setPwd(cursor.getString(2));
                            ld.setProgramId(cursor.getString(3));
                            ld.setLevel(cursor.getInt(4));
                            ld.setSign(cursor.getString(5));

                            byte[] in=cursor.getBlob(6);
                            Bitmap bmpOut=BitmapFactory.decodeByteArray(in,0,in.length);
                            ld.setImg(bmpOut);
                            ld.setIsLogin(true);

                            cursor.close();
                        } else {
                            showNormalDialog(2);
                        }
                    }
                }
        );
        //注册按钮
        btn_resign.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(i);
                    }
                }
        );
        //跳过
        btn_skip.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(View v) {
                        Logined ld =new Logined();
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.people);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        ld.setImg(bitmap);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );

    }
    private void showNormalDialog(final int mes){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);
        if(mes==1) {
            normalDialog.setTitle("登录成功");
            normalDialog.setMessage("");
        }

        else{
            normalDialog.setTitle("登录失败");
            normalDialog.setMessage("密码或用户名错误");
        }

        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mes==1){
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }

                    }
                });
        normalDialog.show();
    }

    //数据库
    @SuppressLint("WrongConstant")
    public void runDatabaseLogin(){
        Log.i(DEBUG_TAG, "login.....");
        if (Arrays.binarySearch(databaseList(), DATABASE_NAME) >= 0) {
            // Delete the old database file, if it exists
            deleteDatabase(DATABASE_NAME);//最后注销
            mDatabase = openOrCreateDatabase(DATABASE_NAME,
                    SQLiteDatabase.CREATE_IF_NECESSARY, null);
            mDatabase.execSQL(CREATE_LOGIN_TABLE);//最后注销
            mDatabase.execSQL(CREATE_SCENERY_TABLE);//最后注销
            addSomeThing();//最后注销
        }
        else{
            mDatabase = openOrCreateDatabase(DATABASE_NAME,
                    SQLiteDatabase.CREATE_IF_NECESSARY, null);
            // Log some information about our database
            Log.i(DEBUG_TAG, "Created database: " + mDatabase.getPath());
            Log.i(DEBUG_TAG, "Database Version: " + mDatabase.getVersion());
            Log.i(DEBUG_TAG, "Database Page Size: " + mDatabase.getPageSize());
            Log.i(DEBUG_TAG, "Database Max Size: " + mDatabase.getMaximumSize());
            Log.i(DEBUG_TAG, "Database Open?  " + mDatabase.isOpen());
            Log.i(DEBUG_TAG, "Database readonly?  " + mDatabase.isReadOnly());
            Log.i(DEBUG_TAG, "Database Locked by current thread?  "
                    + mDatabase.isDbLockedByCurrentThread());
            Log.i(DEBUG_TAG,
                    "Create the tbl_login table using SQLiteStatement.execute()");
            mDatabase.execSQL(CREATE_LOGIN_TABLE);
            mDatabase.execSQL(CREATE_SCENERY_TABLE);
            addSomeThing();
        }

    }
    private void addSomeThing() {
        Log.i(DEBUG_TAG, "Database Transaction Start");
        mDatabase.beginTransaction();
        try {
            // Log some transaction diagnostics
            Log.i(DEBUG_TAG, "Database Transaction?  "
                    + mDatabase.inTransaction());
            Log.i(DEBUG_TAG, "Database Locked by current thread?  "
                    + mDatabase.isDbLockedByCurrentThread());
            // ADD SOME VALUES
            addID(new User("admin","admin","100001",0,"这个人懒死了什么都没有写"));
            addID(new User("123","123","100002",0,"这个人懒死了什么都没有写"));
            addScenery(new Scenery("西溪国家湿地公园","浙江杭州西溪国家湿地公园位于浙江省杭州市区西部,是中国第一个集城市湿地、农耕湿地、文化湿地于一体的国家级湿地公园",
                    "Official website",123456,1));
            addScenery(new Scenery("灵隐景区","中国佛教著名的“十刹”之一","Official website",12345,2));
            addScenery(new Scenery("故宫博物馆","世界上现存规模最大、保存最为完整的木质结构古建筑之一，国家5A级旅游景区","Official website",1234,3));
            addScenery(new Scenery("天安门广场","世界上最大的城市广场","Official website",123,4));
            addScenery(new Scenery("杭州动物园","中国七大动物园之一","Official website",12,5));
            addScenery(new Scenery("长城","中国最长的长城，著名的北京十六景之一","Official website",1,6));
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i(DEBUG_TAG,"Transaction failed. Exception: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
        Log.i(DEBUG_TAG, "Database Transaction End");
    }
    private void addID(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.name);
        values.put("password", user.pwd);
        values.put("programId", user.programId);
        values.put("level", user.level);
        values.put("sign", user.sign);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headimage);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        values.put("headImg", os.toByteArray());

        user.id = mDatabase.insertOrThrow(TABLE_LOGIN, null, values);
        Log.i(DEBUG_TAG, "Added user");
    }
    private void addScenery(Scenery scenery){
        ContentValues values = new ContentValues();
        values.put("name", scenery.name);
        values.put("info", scenery.info);
        values.put("username", scenery.username);
        values.put("favorite", scenery.favorite);
        values.put("classify", scenery.classify);
        Bitmap bitmap = null;
        if(upup==0){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xixishidi);upup++;
        }
        else if(upup==1){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lingyintitle);upup++;
        }
        else if(upup==2){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gugongtitle);upup++;
        }
        else if(upup==3){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tianantitle);upup++;
        }
        else if(upup==4){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dongwuyauntitle);upup++;
        }
        else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.changchentitle);
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
        values.put("sceneryImg", os.toByteArray());

        scenery.id = mDatabase.insertOrThrow(TABLE_SCENERY, null, values);
        Log.i(DEBUG_TAG, "Added scenery");
    }
}
