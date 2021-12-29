package com.example.travel.DataBase;

import android.graphics.Bitmap;

public class User{

    public long id;
    public String name;
    public String pwd;
    public String programId;
    public int level;
    public String sign;
    public Bitmap img ;
    public User(String name, String pwd,String programId,int level,String sign) {
        this.name = name;
        this.pwd = pwd;
        this.programId=programId;
        this.level=level;
        this.sign=sign;
    }

    public User(){}

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


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd=" + pwd +
                '}';
    }
}
