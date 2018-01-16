package yxdroid.droidfm.mvp.presenter;

import android.os.Bundle;

import yxdroid.droidfm.http.retrofit.RxActionManagerImpl;
import yxdroid.droidfm.base.BaseMVPActivity;
import yxdroid.droidfm.mvp.LifeCycleListener;
import yxdroid.droidfm.utils.MD5Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BasePresenter<T, P> implements LifeCycleListener {

    protected WeakReference<T> mViewReference;
    protected WeakReference<P> mActivityReference;

    private List<String> tagList = new ArrayList<>();

    public void attachView(T view, P activity) {
        mViewReference = new WeakReference<>(view);
        mActivityReference = new WeakReference<>(activity);
        if (activity instanceof BaseMVPActivity) {
            ((BaseMVPActivity) activity).setmLifeCycleListener(this);
        }
    }

    public void detachView() {
        if (isAttachView()) {
            mViewReference.clear();
            mViewReference = null;
        }
    }

    public void detachActivity() {
        if (isAttachActivity()) {
            mActivityReference.clear();
            mActivityReference = null;
        }
    }

    protected boolean isAttachView() {
        if (mViewReference == null || mViewReference.get() == null) {
            return false;
        }
        return true;
    }

    protected boolean isAttachActivity() {
        if (mActivityReference == null || mActivityReference.get() == null) {
            return false;
        }
        return true;
    }

    protected T getView() {
        if (isAttachView()) {
            return mViewReference.get();
        }
        return null;
    }

    protected P getActivity() {
        if (mActivityReference != null) {
            return mActivityReference.get();
        }
        return null;
    }

    protected String getTag() {
        T view = getView();
        if (view != null) {
            String method = Thread.currentThread().getStackTrace()[3].getMethodName();
            String tag = MD5Util.md5Encrypt(view.getClass().getName()) + "_" + method;
            tagList.add(tag);
            return tag;
        }
        return "";
    }

    public void cancelHttpRequest() {
        for (String tag : tagList) {
            RxActionManagerImpl.getInstance().cancel(tag);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        detachView();
        detachActivity();
        cancelHttpRequest();
    }
}
