package com.santu.gxxc.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.santu.gxxc.R;
import com.santu.gxxc.adapter.NewsDetailImagesAdapter;
import com.santu.gxxc.http.JsoupManager;
import com.santu.gxxc.http.URLs;
import com.santu.gxxc.model.TextNewsModel;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.utils.Utility;
import com.umeng.analytics.MobclickAgent;

public class TextNewsDetailActivity extends BaseShareActivity {

    private static final String TAG = "TextNewsDetailActivity";

    private TextView tvTitle, tvInfo, tvContent;
    private GridView gvImages;
    private ProgressBar progressBar;
    private String url;
    private String title = "每日新闻";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_news_detail);
        initViews();
        url = getIntent().getStringExtra("url");
        refresh(url);
    }

    private void initViews() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
                if(textNewsModel != null)
                    updateViews(textNewsModel);
                progressBar.setVisibility(View.GONE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weibo:
                shareWeibo();
                break;
            case R.id.weixin:
                break;
            case R.id.qq:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareWeibo() {
        WebpageObject object = new WebpageObject();
        object.title = title;
        object.actionUrl = URLs.BASE_URL + url;
        object.identify = Utility.generateGUID();
        object.defaultText = "热点新闻";
        String descr = title;
        object.description = descr;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_weibo_share);
        object.setThumbImage(bitmap);
        WeiboMessage message = new WeiboMessage();
        message.mediaObject = object;
        shareToWeibo(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }
}
