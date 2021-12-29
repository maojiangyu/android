package com.example.travel.HomePage.home;

public class RankItem {
    private int imageId;
    private String title;
    private String address;
    private String comments;

    RankItem(int imageId,String title,String address,String comments){
        this.imageId = imageId;
        this.address = address;
        this.title = title;
        this.comments = comments;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getComments() {
        return comments;
    }
}
