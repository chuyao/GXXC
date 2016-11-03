package com.santu.gxxc.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.santu.gxxc.http.JsoupManager;
import com.santu.gxxc.model.TextNewsModel;

import java.util.List;

/**
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsListTaskLoader extends AsyncTaskLoader<List<TextNewsModel>> {

    private JsoupManager manager;

    public TextNewsListTaskLoader(Context context) {
        super(context);
        manager = JsoupManager.getInstance();
    }

    @Override
    public List<TextNewsModel> loadInBackground() {
        return manager.getDailyNews(0);
    }

}
