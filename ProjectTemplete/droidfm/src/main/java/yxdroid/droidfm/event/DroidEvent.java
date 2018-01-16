package yxdroid.droidfm.event;

import org.greenrobot.eventbus.EventBus;

public class DroidEvent {

    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    public static void postEvent(MessageEvent messageEvent) {
        EventBus.getDefault().post(messageEvent);
    }

    public static <T, P> void postEvent(T id, P value) {
        MessageEvent<T, P> messageEvent = new MessageEvent<>(id, value);
        postEvent(messageEvent);
    }
}
