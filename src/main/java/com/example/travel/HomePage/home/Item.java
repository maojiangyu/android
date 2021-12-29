package com.example.travel.HomePage.home;

import androidx.annotation.DrawableRes;

public class Item {
    private int mDrawableRes;

    private String mTitle;
    private String mContent;

    public Item(@DrawableRes int drawable, String title,String content) {
        mDrawableRes = drawable;
        mTitle = title;
        mContent = content;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getContent(){return mContent;}

}
