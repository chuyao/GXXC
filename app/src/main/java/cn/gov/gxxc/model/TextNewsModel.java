package cn.gov.gxxc.model;

import java.util.List;

/**
 * Created by ChuyaoShi on 16/10/23.
 */

public class TextNewsModel extends NewsModel {

    private String content;
    private List<String> images;

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
