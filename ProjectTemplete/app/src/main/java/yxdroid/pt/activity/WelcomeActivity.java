package yxdroid.pt.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import yxdroid.droidfm.base.BaseMVPActivity;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.pt.R;
import yxdroid.pt.bean.Token;
import yxdroid.pt.presenter.WelcomePresenter;
import yxdroid.pt.view.IWelcomeView;

public class WelcomeActivity extends BaseMVPActivity
        <IWelcomeView, WelcomePresenter, WelcomeActivity> implements IWelcomeView {

    private static final int READ_PHONE_STATE = 0x01;

    @Override
    protected int bindLayout() {
        return R.layout.activity_welcome;
    }

    @AfterPermissionGranted(READ_PHONE_STATE)
    @Override
    protected void onInit() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
            mPresenter.initToken(this);
        } else {
            EasyPermissions.requestPermissions(this, "", READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    protected BasePresenter<IWelcomeView, WelcomeActivity> createPresenter() {
        return new WelcomePresenter();
    }


    @Override
    public void onShowLoading() {
        Logger.i("onShowLoading");
    }

    @Override
    public void onCloseLoading() {
        Logger.i("onCloseLoading");
    }

    @Override
    public void onGetTokenSuccess(Token token) {
        Logger.i("onGetTokenSuccess");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                close();
            }
        }, 3000);


    }
}
