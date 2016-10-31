package cn.gov.gxxc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsDetailModel implements Serializable {

    private String title;
    private String info;
    private String content;
    private List<String> images;

    public TextNewsDetailModel(String title, List<String> images, String content, String info) {
        this.title = title;
        this.images = images;
        this.content = content;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
