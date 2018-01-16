package yxdroid.pt;

import android.os.Environment;

public class AppConstans {

    public static final String APP_DIR_ROOT = Environment.getExternalStorageDirectory() + "/ProjectTemplete";

    // crash log dir
    public static final String ERROR_LOG_PATH = APP_DIR_ROOT + "/log";

    // cache dir
    public static final String CACHE_PATH = APP_DIR_ROOT + "/cache";
}
