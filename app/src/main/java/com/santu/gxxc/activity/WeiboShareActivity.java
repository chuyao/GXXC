package com.santu.gxxc.activity;

import android.content.Intent;
import android.os.Bundle;

import com.santu.gxxc.sns.Constants;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

/**
 * Created by ChuyaoShi on 16/11/8.
 */

public class WeiboShareActivity extends BaseActivity implements IWeiboHandler.Response{

    private IWeiboShareAPI weiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeiboShare();
    }

    private void initWeiboShare() {
        weiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.WEIBO_APP_ID);
        weiboShareAPI.registerApp();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {

    }
}
