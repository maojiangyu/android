package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.travel.DataBase.Scenery;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Scenery> sceneryList = new ArrayList<>();
    public static final String DEBUG_TAG = "SimpleDB Log";
    public static final String DATABASE_NAME = "travel1.db";
    public static final String TABLE_LOGIN = "tbl_login";
    public static final String TABLE_SCENERY = "tbl_scenery";
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ArrayList<Scenery> sl = new ArrayList<>();
        sl = runDatabase();

        setSceneryList(sl);

    }
    //获取数据库信息
    @SuppressLint("WrongConstant")
    public ArrayList<Scenery> runDatabase() {
        ArrayList<Scenery> msg = new ArrayList<>();
        mDatabase = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String sql = "select * from tbl_scenery where classify > ? ";
        Cursor cursor = null;
        try{
            cursor = mDatabase.rawQuery(sql, new String[]{"0"});
            Scenery s =new Scenery();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                s =new Scenery();
                s.setId(cursor.getLong(0));
                s.setName(cursor.getString(1));
                s.setInfo(cursor.getString(2));
                s.setUsername(cursor.getString(3));
                s.setFavorite(cursor.getInt(4));
                s.setClassify(cursor.getInt(5));

                byte[] in=cursor.getBlob(6);
                Bitmap bmpOut= BitmapFactory.decodeByteArray(in,0,in.length);
                s.setImg(bmpOut);

                msg.add(s);
                Log.d("add to list","success");
            }
        }catch(Exception e){
            Log.d("数据加载", String.valueOf(e));
        }finally {
            cursor.close();
        }
        return msg;
    }
    //发送给fragment的set和get
    public void setSceneryList(ArrayList<Scenery> sl){
        this.sceneryList=sl;
    }
    public ArrayList<Scenery> getSceneryList(){
        return this.sceneryList;
    }


}