package com.santu.gxxc.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.santu.gxxc.R;

import net.youmi.android.AdManager;
import net.youmi.android.nm.cm.ErrorCode;
import net.youmi.android.nm.sp.SplashViewSettings;
import net.youmi.android.nm.sp.SpotListener;
import net.youmi.android.nm.sp.SpotManager;
import net.youmi.android.nm.sp.SpotRequestListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 0x01;

    private String[] perms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(EasyPermissions.hasPermissions(this, perms)) {
            runAd();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCodeQRCodePermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }

    private void runAd() {
        AdManager.getInstance(this).init("907e93b43d7510f3", "e6d9ffc00641e41f", true);
        preloadAd();
        setupSplashAd();
    }

    /**
     * 预加载广告
     */
    private void preloadAd() {
        // 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                //				// 应用安装后首次展示开屏会因为本地没有数据而跳过
                //              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
                //				setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        break;
                    case ErrorCode.NON_AD:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器
        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        //		// 设置是否展示失败自动跳转，默认自动跳转
        //		splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
        // 设置跳转的窗口类
        splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        // 展示开屏广告
        SpotManager.getInstance(this)
                .showSplash(this, splashViewSettings, new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                break;
                            case ErrorCode.NON_AD:
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onSpotClosed() {
                    }

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                    }
                });
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "没有基本的权限，我们无法为你提供更好的服务~", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(EasyPermissions.hasPermissions(this, SplashActivity.this.perms)) {
            runAd();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
