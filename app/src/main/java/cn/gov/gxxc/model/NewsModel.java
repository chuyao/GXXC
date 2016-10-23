package cn.gov.gxxc.model;

import java.io.Serializable;

/**
 * Created by ChuyaoShi on 16/10/23.
 */

public class NewsModel implements Serializable {

    private String title;
    private String time;
    private String author;
    private String origin;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
