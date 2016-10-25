package cn.gov.gxxc.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.gov.gxxc.model.TextNewsModel;
import cn.gov.gxxc.model.VideoNewsModel;

/**
 * Created by ChuyaoShi on 16/10/22.
 */

public class JsoupManager {

    private static JsoupManager manager = new JsoupManager();

    public static JsoupManager getInstance() {
        return manager;
    }

    private JsoupManager () {
    }

    /**
     * 文本新闻列表获取
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
            for(Element e : elements) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 视频新闻列表获取
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
            for(Element e : elements) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
