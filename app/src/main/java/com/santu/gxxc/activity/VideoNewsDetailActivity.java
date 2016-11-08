package com.santu.gxxc.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.santu.gxxc.R;
import com.santu.gxxc.http.JsoupManager;
import com.santu.gxxc.http.URLs;
import com.santu.gxxc.model.VideoNewsModel;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.utils.Utility;
import com.umeng.analytics.MobclickAgent;

public class VideoNewsDetailActivity extends BaseShareActivity implements MediaPlayer.OnPreparedListener {

    private static final String TAG = "VideoNewsDetailActivity";

    private VideoView videoView;

    private TextView tvTitle, tvInfo;
    private ProgressBar progressBar;

    private MediaController mediaController;

    private AdView mAdView;

    private int position;

    private String title;

    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_news_detail);
        MobileAds.initialize(this, "ca-app-pub-5065614493130923~9863076690");
        initViews();
        url = getIntent().getStringExtra("url");
        refresh(url);
    }

    private void initViews() {
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        videoView = (VideoView) findViewById(R.id.video_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void refresh(String url) {
        new AsyncTask<String, Integer, VideoNewsModel>() {
            @Override
            protected VideoNewsModel doInBackground(String... params) {
                return JsoupManager.getInstance().getVideoNewsDetail(params[0]);
            }

            @Override
            protected void onPostExecute(VideoNewsModel videoNewsModel) {
                super.onPostExecute(videoNewsModel);
                if (videoNewsModel != null)
                    updateViews(videoNewsModel);
                else
                    progressBar.setVisibility(View.GONE);
            }
        }.execute(url);
    }

    //一周要闻片头 12秒
    //每日新闻片头 37秒

    private void updateViews(VideoNewsModel model) {
        title = model.getTitle();
        tvInfo.setText(model.getInfo());
        tvTitle.setText(title);
        videoView.setVideoPath(model.getPath());
        videoView.setOnPreparedListener(this);
        videoView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        progressBar.setVisibility(View.GONE);
//        if (position == 0) {
//            if (title.contains("忻城新闻")) {
//                videoView.seekTo(37);
//            } else if (title.contains("一周要闻")) {
//                videoView.seekTo(12);
//            }
//        }
        mediaController.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        if (mAdView != null) {
            mAdView.resume();
        }
        if (position != 0) {
            progressBar.setVisibility(View.VISIBLE);
            videoView.seekTo(position);
            videoView.start();
        }
    }

    private void shareWeibo() {
        WebpageObject object = new WebpageObject();
        object.title = title;
        object.actionUrl = URLs.BASE_URL + url;
        object.identify = Utility.generateGUID();
        object.defaultText = "视频新闻";
        String descr = title;
        if(title.contains("忻城新闻")) {
            descr = "每日新闻：" + title;
        }
        if(title.contains("一周要闻")) {
            descr = "一周要闻：" + title;
        }
        object.description = descr;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_weibo_share);
        object.setThumbImage(bitmap);
        WeiboMessage message = new WeiboMessage();
        message.mediaObject = object;
        shareToWeibo(message);
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

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
        if (mAdView != null) {
            mAdView.pause();
        }
        position = videoView.getCurrentPosition();
        videoView.stopPlayback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}
