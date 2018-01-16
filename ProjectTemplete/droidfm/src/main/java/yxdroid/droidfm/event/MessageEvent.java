package yxdroid.droidfm.event;

public class MessageEvent<T, P> {
    private T id;
    private P value;

    public MessageEvent(T id, P value) {
        this.id = id;
        this.value = value;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public P getValue() {
        return value;
    }

    public void setValue(P value) {
        this.value = value;
    }
}
