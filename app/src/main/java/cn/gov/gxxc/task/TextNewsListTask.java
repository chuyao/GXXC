package cn.gov.gxxc.task;

import android.os.AsyncTask;

import java.util.List;

import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.TextNewsModel;

/**
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsListTask extends AsyncTask<Integer, Integer, List<TextNewsModel>> {

    private JsoupManager manager;

    public TextNewsListTask() {
        manager = JsoupManager.getInstance();
    }

    @Override
    protected List<TextNewsModel> doInBackground(Integer... params) {
        return manager.getDailyNews(params[0]);
    }
}
