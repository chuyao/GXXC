package cn.gov.gxxc.model;

import java.util.List;

/**
 * Created by ChuyaoShi on 16/10/23.
 */

public class TextNewsModel extends NewsModel {

    private String info;
    private String content;
    private List<String> images;

    public TextNewsModel() {
    }

    public TextNewsModel(String title, String info, String content, List<String> images) {
        this.info = info;
        this.content = content;
        this.images = images;
        super.setTitle(title);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
