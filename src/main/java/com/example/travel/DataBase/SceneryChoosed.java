package com.example.travel.DataBase;

import android.graphics.Bitmap;

public class SceneryChoosed {
    public static long id;
    public static String name;
    public static String info;
    public static String username;
    public static int favorite;
    public static int classify;
    public static Bitmap img;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public String getUsername() { return username; }

    public String getName() { return name; }

    public void setClassify(int classify) { this.classify = classify; }

    public int getClassify() { return classify; }

    public void setFavorite(int favorite) { this.favorite = favorite; }

    public int getFavorite() { return favorite; }

    public void setName(String name) { this.name = name; }

    public void setUsername(String username) { this.username = username; }

    public Bitmap getImg() { return img; }

    public void setImg(Bitmap img) { this.img = img; }


}
