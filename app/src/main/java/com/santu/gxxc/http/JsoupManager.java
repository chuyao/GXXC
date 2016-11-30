package com.santu.gxxc.http;

import com.santu.gxxc.model.TextNewsModel;
import com.santu.gxxc.model.VideoNewsModel;
import com.santu.gxxc.model.WeatherModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuyaoShi on 16/10/22.
 */

public class JsoupManager {

    private static JsoupManager manager = new JsoupManager();

    public static JsoupManager getInstance() {
        return manager;
    }

    private JsoupManager() {
    }

    /**
     * 文本新闻列表获取
     *
     * @param page
     * @return
     */
    public List<TextNewsModel> getDailyNews(int page) {
        String url = URLs.DAILY_NEWS + "?p=" + page;
        List<TextNewsModel> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.select("div.article-list").first();
            Elements elements = element.select("li");
            for (Element e : elements) {
                Element e1 = e.select("a").first();
                String link = e1.attr("href");
                String title = e1.attr("title");
                String date = e.getElementsByClass("date").first().text();
                TextNewsModel model = new TextNewsModel();
                model.setTitle(title);
                model.setDate(date);
                model.setUrl(link);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 文本新闻详情获取
     * @param url
     * @return
     */
    public TextNewsModel getTextNewsDetail(String url) {
        String detailUrl = URLs.BASE_URL + url;
        TextNewsModel model = null;
        try {
            Document doc = Jsoup.connect(detailUrl).get();
            Element element = doc.select("div.article").first();
            String title = element.select("h1.art-title").first().text();
            String info = element.select("div.art-info").first().text();
            info = info.substring(0, info.indexOf("来源"));
            Element contentElement = element.select("div.art-content").first();
            List<String> images = new ArrayList<>();
            if (!contentElement.getElementsByTag("img").isEmpty()) {
//                Elements children = contentElement.children();
//                for (int i = children.size() - 1; i > 0; i--) {
//                    if (!contentElement.child(i).getElementsByTag("img").isEmpty()) {
//                        String image = contentElement.child(i).select("img").first().attr("src");
//                        images.add(image);
//                        contentElement.getElementsByIndexGreaterThan(i).remove();
//                    }
//                }
                Elements elements = contentElement.select("img");
                for(Element e : elements) {
                    images.add(URLs.BASE_URL + e.attr("src"));
                }
            }
            String content = contentElement.text();
            model = new TextNewsModel(title, info, content, images);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 视频新闻列表获取
     *
     * @param page
     * @return
     */
    public List<VideoNewsModel> getVideoNews(int page) {
        List<VideoNewsModel> list = new ArrayList<>();
        String url = URLs.VIDEO_NEWS + "?p=" + page;
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.select("div.article-list").first();
            Elements elements = element.select("li");
            for (Element e : elements) {
                Element e1 = e.select("a").first();
                String link = e1.attr("href");
                String title = e1.attr("title");
                String date = e.getElementsByClass("date").first().text();
                VideoNewsModel model = new VideoNewsModel();
                model.setTitle(title);
                model.setDate(date);
                model.setUrl(link);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public VideoNewsModel getVideoNewsDetail(String url) {
        VideoNewsModel model = null;
        url = URLs.BASE_URL + url;
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.select("div.article").first();
            String title = element.select("h1.art-title").first().text();
            String info = element.select("div.art-info").first().text();
            info = info.substring(0, info.indexOf("来源"));
            Element videoElement = element.select("video").first();
            String path = videoElement.select("source").first().attr("src");
            model = new VideoNewsModel(title, info, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 获取天气预报信息
     * @return
     */
    public List<WeatherModel> getWeather() {
        List<WeatherModel> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URLs.WEATHER_MORE).get();
            doc.select("div.wtleft").remove();
            Element element = doc.getElementById("mobile4");
            Elements elements = element.getElementsByTag("a");
            for(Element el : elements) {
                String detail = el.attr("title");
                String date = el.select("div.wtline").get(0).text();
                String image = el.getElementsByClass("pngtqico").get(0).attr("src");
                list.add(new WeatherModel(date, detail, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
