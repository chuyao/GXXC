package cn.gov.gxxc.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.TextNewsModel;

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
