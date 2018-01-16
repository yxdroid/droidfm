package yxdroid.pt;

import com.orhanobut.logger.Logger;

import yxdroid.droidfm.base.BaseApplication;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initCrashHandler(AppConstans.ERROR_LOG_PATH);
        initLogSystem("PT");
        initImageLoader(AppConstans.CACHE_PATH);

        Logger.i(this.getClass().getSimpleName() + " onCreate");
    }

    @Override
    protected String httpBaseApi() {
        return "http://beta.passport.api.vrseen.net/";
    }
}
