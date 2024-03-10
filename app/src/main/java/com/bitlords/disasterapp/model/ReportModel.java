package com.bitlords.disasterapp.model;

public class ReportModel {
    String type,postCode,area,about;

    public ReportModel(String type, String postCode, String area, String about) {
        this.type = type;
        this.postCode = postCode;
        this.area = area;
        this.about = about;
    }

    public ReportModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
