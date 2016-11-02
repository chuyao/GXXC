package cn.gov.gxxc.activity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import cn.gov.gxxc.R;
import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.VideoNewsModel;

public class VideoNewsDetailActivity extends BaseActivity implements MediaPlayer.OnPreparedListener{

    private VideoView videoView;
    private TextView tvTitle, tvInfo;

    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_news_detail);
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
    protected void onStop() {
        super.onStop();
//        mediaController
    }
}