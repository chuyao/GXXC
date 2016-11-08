package com.santu.gxxc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.santu.gxxc.R;
import com.santu.gxxc.sns.Constants;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by ChuyaoShi on 16/11/7.
 */

public class BaseShareActivity extends BaseActivity implements IWeiboHandler.Response{

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


    protected void shareToWeibo(WeiboMessage message){
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = message;
        weiboShareAPI.sendRequest(this, request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onResponse(BaseResponse baseResponse) {
        if(baseResponse != null) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    break;
            }
        }
    }
}
