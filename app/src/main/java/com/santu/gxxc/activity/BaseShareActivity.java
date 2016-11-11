package com.santu.gxxc.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.santu.gxxc.R;
import com.santu.gxxc.sns.Constants;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by ChuyaoShi on 16/11/7.
 */

public class BaseShareActivity extends BaseActivity implements IWeiboHandler.Response{

    private IWeiboShareAPI weiboShareAPI;
    private IWXAPI mIWXAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeiboShare();
        initWeiXinShare();
    }

    private void initWeiboShare() {
        weiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.WEIBO_APP_ID);
        weiboShareAPI.registerApp();
    }

    private void initWeiXinShare() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, true);
        mIWXAPI.registerApp(Constants.WEIXIN_APP_ID);
    }

    protected void shareToWeibo(WeiboMessage message){
        if(!weiboShareAPI.isWeiboAppInstalled()) {
            Toast.makeText(this, "没有检测到微博客户端，请安装后再分享", Toast.LENGTH_SHORT).show();
            return;
        }
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = message;
        weiboShareAPI.sendRequest(this, request);
    }

    protected void shareToWeixin(WXMediaMessage message, int scene, String type) {
        if(!mIWXAPI.isWXAppInstalled()){
            Toast.makeText(this, "没有检测到微信客户端，请安装后再分享", Toast.LENGTH_SHORT).show();
            return;
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = message;
        req.scene = scene;
        req.transaction = buildTransaction(type);
        mIWXAPI.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
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
                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    break;
            }
        }
    }
}
