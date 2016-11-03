package com.santu.gxxc.model;

/**
 * Created by ChuyaoShi on 16/10/23.
 */

public class VideoNewsModel extends NewsModel {

    private String info;

    private String path;

    public VideoNewsModel() {
    }

    public VideoNewsModel(String title, String info, String path) {
        super.setTitle(title);
        this.info = info;
        this.path = path;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
