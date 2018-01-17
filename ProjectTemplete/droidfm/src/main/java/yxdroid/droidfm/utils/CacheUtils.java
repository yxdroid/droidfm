package yxdroid.droidfm.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CacheUtils {
    public static final String TAG = "CacheUtils";

    /**
     * 保存序列化对象
     *
     * @param ser
     * @param key
     */
    public static boolean saveSerializableObject(Context context, Serializable ser, String key) {
        FileOutputStream fos = null;
        ObjectOutputStream obos = null;
        try {
            fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            obos = new ObjectOutputStream(fos);
            obos.writeObject(ser);
            obos.flush();
            return true;
        } catch (Exception e) {
            Logger.e("序列化保存失败 = " + e.getMessage());
            return false;
        } finally {
            if (obos != null) {
                try {
                    obos.close();
                    obos = null;
                } catch (IOException e) {

                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }

            }
        }
    }

    /**
     * 获取保存的序列化对象
     *
     * @param key
     * @return
     */
    public static Serializable readSerializableObject(Context context, String key) {
        if (context == null || !checkCacheFileIsExist(context, key)) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream obis = null;
        try {
            fis = context.openFileInput(key);
            obis = new ObjectInputStream(fis);
            return (Serializable) obis.readObject();
        } catch (Exception e) {
            // 反序列化失败 删除该文件
            if (e instanceof InvalidClassException) {
                File cacheFile = context.getFileStreamPath(key);
                if (cacheFile != null) {
                    cacheFile.delete();
                }
            }

            return null;
        } finally {
            if (obis != null) {
                try {
                    obis.close();
                    obis = null;
                } catch (IOException e) {
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 检查缓存文件是否存在
     *
     * @param key
     * @return
     */
    private static boolean checkCacheFileIsExist(Context context, String key) {
        File cacheFile = context.getFileStreamPath(key);
        return !(cacheFile == null || !cacheFile.exists());
    }

    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache(Context context, String cachePath) {

        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除webview cookie
     *
     * @param context
     */
    public static void clearWebViewCookie(Context context) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        } catch (Exception e) {
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

}
