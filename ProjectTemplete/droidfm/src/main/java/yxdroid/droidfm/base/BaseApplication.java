package yxdroid.droidfm.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import yxdroid.droidfm.CrashHandler;
import yxdroid.droidfm.http.retrofit.RetrofitBuilder;
import yxdroid.droidfm.utils.ImageLoader;

import java.io.File;

import okhttp3.logging.HttpLoggingInterceptor;

import static android.content.pm.PackageManager.GET_META_DATA;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitBuilder.getInstance().initHttp(httpBaseApi(), HttpLoggingInterceptor.Level.NONE);
    }

    protected void initLogSystem(String tag) {

        // onInit log system by logger lib
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(3)
                .methodOffset(7)
                .tag(tag)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return logger();
            }
        });
    }

    protected void initImageLoader(String cacheDir) {
        ImageLoader.initialize(this, new File(cacheDir));
    }

    protected void initCrashHandler(String logDir) {
        CrashHandler.getInstance().init(this, logDir);
    }

    protected abstract String httpBaseApi();

    private boolean logger() {
        Bundle metaData = null;
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),
                    GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                return metaData.getBoolean("logger", false);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
