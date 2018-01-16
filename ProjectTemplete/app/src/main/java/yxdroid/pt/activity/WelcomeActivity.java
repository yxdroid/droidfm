package yxdroid.pt.activity;

import android.Manifest;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import yxdroid.droidfm.base.BaseMVPActivity;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.pt.bean.Token;
import yxdroid.pt.presenter.WelcomePresenter;
import yxdroid.pt.view.IWelcomeView;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends BaseMVPActivity
        <IWelcomeView, WelcomePresenter, WelcomeActivity> implements IWelcomeView {

    private static final int READ_PHONE_STATE = 0x01;

    @Override
    protected int bindLayout() {
        return 0;
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
    protected void onBack() {

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
        startActivity(new Intent(this, MainActivity.class));
        close();
    }
}
