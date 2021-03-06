package yxdroid.droidfm.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;
import yxdroid.droidfm.ActivityStatckManager;
import yxdroid.droidfm.fragment.FragmentCommunication;
import yxdroid.droidfm.mvp.LifeCycleListener;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.droidfm.mvp.view.IBaseView;
import yxdroid.droidfm.view.CustomActionBar;

public abstract class BaseMVPActivity<T, V extends BasePresenter, P extends BaseMVPActivity>
        extends RxFragmentActivity implements IBaseView, EasyPermissions.PermissionCallbacks, CustomActionBar.ActionBarListener {

    protected V mPresenter;

    private LifeCycleListener mLifeCycleListener;

    private boolean isSetFragmentCommunicationInterface = false;

    private CustomActionBar customActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStatckManager.getInstance().addActivity(this);

        int layoutId = bindLayout();

        if (layoutId != 0) {
            setContentView(layoutId);
        }

        //setStatusBarStyle();

        ButterKnife.bind(this);

        mPresenter = (V) createPresenter();

        if (mPresenter != null) {
            mPresenter.attachView(this, this);
        }

        if (mLifeCycleListener != null) {
            mLifeCycleListener.onCreate(savedInstanceState);
        }

        onInit();

        Logger.i("onCreate " + getLocalClassName());
    }

    private void setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(android.R.color.holo_green_light);

            // 沉浸式任务栏
            ViewGroup contentFrameLayout = findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("onStart " + getLocalClassName());
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i("onRestart " + getLocalClassName());
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("onResume " + getLocalClassName());
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("onPause " + getLocalClassName());
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("onStop " + getLocalClassName());
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy " + getLocalClassName());

        ButterKnife.unbind(this);

        if (mLifeCycleListener != null) {
            mLifeCycleListener.onDestroy();
        }

        if (mPresenter != null) {
            mPresenter = null;
        }

        if (isSetFragmentCommunicationInterface) {
            removeFragmentCommunicationInterface();
        }

        ActivityStatckManager.getInstance().finishActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onCloseLoading() {

    }

    public void showTip(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showTip(int resId) {
        showTip(getString(resId));
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void close() {
        ActivityStatckManager.getInstance().finishActivity(this);
    }

    public void setmLifeCycleListener(LifeCycleListener mLifeCycleListener) {
        this.mLifeCycleListener = mLifeCycleListener;
    }

    /**
     * 设置fragment 通信接口
     * 子类实现具体接口函数
     *
     * @param tag
     */
    public void setFragmentCommunicationInterface(String tag) {
        // subclass implements
        isSetFragmentCommunicationInterface = true;
    }

    public void removeFragmentCommunicationInterface() {
        FragmentCommunication.getInstance().remove(getClass().getName());
    }

    /**
     * 初始化actionBar
     *
     * @param title
     */
    protected void initActionBar(String title) {
        initActionBar(title, 0);
    }

    /**
     * 初始化actionBar
     *
     * @param title
     */
    protected void initActionBar(int title) {
        initActionBar(getString(title), 0);
    }

    protected void initActionBar(String title, int rightImg) {
        if (customActionBar == null) {
            customActionBar = new CustomActionBar(this, this);
        }
        try {
            customActionBar.initActionBar(null, title, rightImg, null);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBack() {

    }

    @Override
    public void onRightBtnClick() {

    }

    @Override
    public void onRightTvClick() {

    }

    protected abstract int bindLayout();

    protected abstract void onInit();

    protected abstract BasePresenter<T, P> createPresenter();
}
