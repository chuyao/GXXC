package com.santu.gxxc.task;

import android.os.AsyncTask;

import com.santu.gxxc.http.JsoupManager;
import com.santu.gxxc.model.TextNewsModel;

import java.util.List;

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
