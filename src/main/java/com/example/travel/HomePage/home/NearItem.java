package com.example.travel.HomePage.home;

public class NearItem {
    private String name;
    private int imageId;
    private String content;
    private String distance;
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public NearItem(String name, int imageId,String distance,String point,String content) {
        this.name = name;
        this.imageId = imageId;
        this.distance = distance;
        this.point = point;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
