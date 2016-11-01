package cn.gov.gxxc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.http.URLs;
import cn.gov.gxxc.model.TextNewsModel;

public class TextNewsDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvInfo, tvContent;


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
    }

    private void refresh(String url){
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
    }
}
