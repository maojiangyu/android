package com.example.travel.DataBase;

import android.graphics.Bitmap;

public class Logined {
    public static long id;
    public static String name ="未登入";
    public static String pwd;
    public static String programId = "未登入";
    public static int level = 0;
    public static String sign = "......";
    public static Bitmap img ;
    public static boolean isLogin = false;
    public Logined(String name, String pwd,String programId,int level,String sign) {
        this.name = name;
        this.pwd = pwd;
        this.programId=programId;
        this.level=level;
        this.sign=sign;
    }

    public Logined(){
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
