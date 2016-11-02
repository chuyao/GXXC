package cn.gov.gxxc.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import cn.gov.gxxc.R;
import cn.gov.gxxc.adapter.NewsDetailImagesAdapter;
import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.TextNewsModel;

public class TextNewsDetailActivity extends BaseActivity {

    private TextView tvTitle, tvInfo, tvContent;
    private GridView gvImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_news_detail);
        initViews();
        String url = getIntent().getStringExtra("url");
        refresh(url);
    }

    private void initViews() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        gvImages = (GridView) findViewById(R.id.gv_images);
        gvImages.setFocusable(false);
    }

    private void refresh(String url) {
        new AsyncTask<String, Integer, TextNewsModel>() {
            @Override
            protected TextNewsModel doInBackground(String... params) {
                return JsoupManager.getInstance().getTextNewsDetail(params[0]);
            }

            @Override
            protected void onPostExecute(TextNewsModel textNewsModel) {
                super.onPostExecute(textNewsModel);
                updateViews(textNewsModel);
            }
        }.execute(url);
    }

    private void updateViews(TextNewsModel model) {
        tvTitle.setText(model.getTitle());
        tvInfo.setText(model.getInfo());
        tvContent.setText(model.getContent());
        updateGridImages(model);
    }

    private void updateGridImages(TextNewsModel model) {
        if (!model.getImages().isEmpty()) {
            gvImages.setNumColumns(model.getImages().size() > 3 ? 3 : model.getImages().size());
            gvImages.setAdapter(new NewsDetailImagesAdapter(this, model.getImages()));
        }
    }
}