package yxdroid.droidfm.http;

import com.orhanobut.logger.Logger;

import yxdroid.droidfm.http.retrofit.RxActionManagerImpl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseSubscriber<T> implements Observer<T>, HttpRequestListener {

    protected HttpCallback callback;

    private String tag;

    public BaseSubscriber(HttpCallback callback, String tag) {
        this.callback = callback;
        this.tag = tag;
    }

    @Override
    public void onSubscribe(Disposable d) {
        RxActionManagerImpl.getInstance().add(tag, d);
        if (callback != null) {
            callback.onStart();
        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        onComplete();
    }

    @Override
    public void onComplete() {
        RxActionManagerImpl.getInstance().remove(tag);
        if (callback != null) {
            callback.onComplete();
        }
    }

    @Override
    public void cancelRequest() {
        RxActionManagerImpl.getInstance().remove(tag);
        Logger.i("can http request");
    }
}
