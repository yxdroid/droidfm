package yxdroid.pt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;
import yxdroid.droidfm.base.BaseMVPActivity;
import yxdroid.droidfm.event.DroidEvent;
import yxdroid.droidfm.event.MessageEvent;
import yxdroid.droidfm.http.ApiException;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.droidfm.utils.ImageLoader;
import yxdroid.droidfm.utils.UnitsConvert;
import yxdroid.pt.R;
import yxdroid.pt.presenter.MainPresenter;
import yxdroid.pt.view.IMainView;

public class MainActivity extends BaseMVPActivity<IMainView, MainPresenter, MainActivity>
        implements IMainView {

    static {
        System.loadLibrary("native-lib");
    }

    @Bind(R.id.sample_text)
    TextView tvSample;

    @Bind(R.id.iv_pic)
    SimpleDraweeView ivPic;

    private ProgressDialog progressDialog;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInit() {
        DroidEvent.register(this);
        tvSample.setText("你来啊");

        ImageLoader.loadImage(ivPic, "http://h.hiphotos.baidu.com/image/pic/item/b7fd5266d016092494670382df0735fae7cd34ec.jpg", UnitsConvert.dip2px(this, 300), UnitsConvert.dip2px(this, 300));
    }

    @Override
    protected void onBack() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DroidEvent.unregister(this);
    }

    @Override
    protected BasePresenter<IMainView, MainActivity> createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onShowLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onCloseLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @OnClick({R.id.sample_text})
    public void onClick(View view) {


        startActivity(new Intent(this, TestActivity.class));

        //mPresenter.login();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(ApiException exception) {
        DroidEvent.postEvent(4, "你好");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<Integer, String> event) {
        showTip(event.getId() + " " + event.getValue());
    }
}
