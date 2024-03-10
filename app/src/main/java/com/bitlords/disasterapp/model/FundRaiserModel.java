package com.bitlords.disasterapp.model;

public class FundRaiserModel {
    String title,subTitle,imgUrl,revAmo,totAmo;

    public FundRaiserModel(String title, String subTitle, String imgUrl, String revAmo, String totAmo) {
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
        this.revAmo = revAmo;
        this.totAmo = totAmo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRevAmo() {
        return revAmo;
    }

    public void setRevAmo(String revAmo) {
        this.revAmo = revAmo;
    }

    public String getTotAmo() {
        return totAmo;
    }

    public void setTotAmo(String totAmo) {
        this.totAmo = totAmo;
    }
}
