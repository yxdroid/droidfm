package yxdroid.droidfm.mvp.model;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yxdroid.droidfm.bean.Result;
import yxdroid.droidfm.http.ApiException;
import yxdroid.droidfm.http.BaseSubscriber;
import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.http.retrofit.RetrofitBuilder;

import static yxdroid.droidfm.http.ApiException.NET_WORK_ERROR;

public abstract class BaseModel {

    protected <T> T getApiService(Class<T> service) {
        T t = RetrofitBuilder.getInstance().build().create(service);
        return t;
    }

    protected <T> T getApiService(Class<T> service, String baseApi) {
        T t = RetrofitBuilder.getInstance().build(baseApi).create(service);
        return t;
    }

    public void addHeader(Map<String, String> headers) {
        RetrofitBuilder.getInstance().initHeaders(headers);
    }

    public void addHeader(String key, String value) {
        RetrofitBuilder.getInstance().addHeader(key, value);
    }

    protected <T> void doHttpRequest(Observable<T> apiObservable, final HttpCallback callback) {
        if (callback == null) {
            return;
        }
        LifecycleProvider lifecycleProvider = callback.getLifecycleProvider();
        if (lifecycleProvider != null) {
            apiObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new HttpSubscriber<T>(callback, callback.getTag()));

        } else {
            apiObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpSubscriber<T>(callback, callback.getTag()));
        }
    }

    class HttpSubscriber<T> extends BaseSubscriber<T> {

        public HttpSubscriber(HttpCallback callback, String tag) {
            super(callback, tag);
        }

        @Override
        public void onNext(T t) {
            Logger.i(t.toString());
            if (callback != null) {
                if (t instanceof Result) {
                    callback.onSuccess(((Result) t).getData());
                } else {
                    callback.onSuccess(t);
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            if (callback == null) {
                return;
            }

            if (e instanceof ApiException) {
                callback.onFailure((ApiException) e);
            } else {
                callback.onFailure(new ApiException(NET_WORK_ERROR, "网络异常"));
            }

            /*if (e instanceof retrofit2.HttpException) {
                Logger.e("http exception");
            } else if (e instanceof IOException) {
                // handle io exception
                Logger.e("IO Exception");
            } else if (e instanceof ApiException) {
                // handle api exption
                Logger.e("ApiException");
            } else {
                Logger.e(e.getMessage());
            }*/
        }
    }
}
