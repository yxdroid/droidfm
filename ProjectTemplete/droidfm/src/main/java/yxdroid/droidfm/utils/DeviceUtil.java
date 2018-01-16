package yxdroid.droidfm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

public class DeviceUtil {

    /**
     * 获取设备id
     *
     * @param context
     * @return 设备id
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String id = "";
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            id = tm.getDeviceId();
            if (TextUtils.isEmpty(id)) {
                id = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }

        } catch (Throwable e) {
            Logger.e("no permission read phone state");
        }
        return id;
    }
}
