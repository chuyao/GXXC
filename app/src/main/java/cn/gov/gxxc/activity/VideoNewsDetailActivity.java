package cn.gov.gxxc.activity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.umeng.analytics.MobclickAgent;

import cn.gov.gxxc.R;
import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.VideoNewsModel;

public class VideoNewsDetailActivity extends BaseActivity implements MediaPlayer.OnPreparedListener{

    private static final String TAG = "VideoNewsDetailActivity";

    private VideoView videoView;
    private TextView tvTitle, tvInfo;

    private MediaController mediaController;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_news_detail);
        MobileAds.initialize(this, "ca-app-pub-5065614493130923~9863076690");
        initViews();
        String url = getIntent().getStringExtra("url");
        refresh(url);
    }

    private void initViews(){
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        videoView = (VideoView) findViewById(R.id.video_view);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
    }

    private void refresh(String url){
        new AsyncTask<String, Integer, VideoNewsModel>() {
            @Override
            protected VideoNewsModel doInBackground(String... params) {
                return JsoupManager.getInstance().getVideoNewsDetail(params[0]);
            }

            @Override
            protected void onPostExecute(VideoNewsModel videoNewsModel) {
                super.onPostExecute(videoNewsModel);
                if(videoNewsModel != null)
                    updateViews(videoNewsModel);
            }
        }.execute(url);
    }

    private void updateViews(VideoNewsModel model) {
        tvInfo.setText(model.getInfo());
        tvTitle.setText(model.getTitle());
        videoView.setVideoPath(model.getPath());
        videoView.setOnPreparedListener(this);
        videoView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaController.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        if(mAdView != null){
            mAdView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
        if(mAdView != null){
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAdView != null){
            mAdView.destroy();
        }
    }
}
