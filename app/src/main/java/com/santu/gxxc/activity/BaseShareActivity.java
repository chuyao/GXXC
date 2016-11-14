package com.santu.gxxc.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.santu.gxxc.R;
import com.santu.gxxc.sns.Constants;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

/**
 * Created by ChuyaoShi on 16/11/7.
 */

public class BaseShareActivity extends BaseActivity {

    private IWeiboShareAPI mWeiboShareAPI;
    private IWXAPI mIWXAPI;
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeiboShare();
        initWeiXinShare();
        initQQShare();
    }

    private void initQQShare() {
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
    }

    private void initWeiboShare() {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.WEIBO_APP_ID);
        mWeiboShareAPI.registerApp();
    }

    private void initWeiXinShare() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, true);
        mIWXAPI.registerApp(Constants.WEIXIN_APP_ID);
    }

    protected void shareToQQ(Bundle bundle) {
        mTencent.shareToQQ(this, bundle, null);
    }

    protected void shareToWeibo(WeiboMessage message) {
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            Toast.makeText(this, "没有检测到微博客户端，请安装后再分享", Toast.LENGTH_SHORT).show();
            return;
        }
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = message;
        mWeiboShareAPI.sendRequest(this, request);
    }

    protected void shareToWeixin(WXMediaMessage message, int scene, String type) {
        if (!mIWXAPI.isWXAppInstalled()) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
