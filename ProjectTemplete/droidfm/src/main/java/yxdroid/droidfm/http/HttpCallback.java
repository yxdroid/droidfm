package yxdroid.droidfm.http;

import com.trello.rxlifecycle2.LifecycleProvider;

public abstract class HttpCallback<T> {

    private LifecycleProvider lifecycleProvider;
    private String tag;

    public HttpCallback(LifecycleProvider lifecycleProvider, String tag) {
        this.lifecycleProvider = lifecycleProvider;
        this.tag = tag;
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycleProvider;
    }

    public String getTag() {
        return tag;
    }

    public void onStart() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(ApiException ex);

    public void onComplete() {

    }
}
