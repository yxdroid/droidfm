package yxdroid.droidfm;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.ListIterator;
import java.util.Stack;

public class ActivityStatckManager {

    private static final String TAG = "AppManager";

    private static Stack<Activity> activityStack;
    private static ActivityStatckManager instance;

    public static boolean appIsOpen = false;

    private ActivityStatckManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityStatckManager getInstance() {
        if (instance == null) {
            instance = new ActivityStatckManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);

        Logger.i("activity stack size = " + activityStack.size());
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束非cls 的所有activity
     *
     * @param cls
     */
    public void finishNotActivity(Class<?> cls) {
        ListIterator<Activity> iterator = activityStack.listIterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context, boolean kill) {
        try {
            finishAllActivity();
            ActivityStatckManager.appIsOpen = false;
            if (kill) {
                ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.killBackgroundProcesses(context.getPackageName());
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
