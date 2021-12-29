package com.example.travel.DataBase;

import android.graphics.Bitmap;

public class Scenery {

    public long id;
    public String name;
    public String info;
    public String username;
    public int favorite;
    public int classify;
    public Bitmap img;
    public int imgPath;

    public Scenery(String name,String info,String username,int favorite,int classify) {
        this.name = name;
        this.favorite = favorite;
        this.info = info;
        this.classify=classify;
        this.username=username;
    }
    public Scenery(String name,String info,int imgPath) {
        this.name = name;
        this.imgPath = imgPath;
        this.info = info;
    }
    public Scenery() {
    }

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

    public void setImgPath(int imgPath) { this.classify = imgPath; }

    public int getImgPath() { return imgPath; }
}
