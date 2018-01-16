package yxdroid.droidfm.http;

public class ApiException extends RuntimeException {

    public static final String NET_WORK_ERROR = "neterror";

    private String code;
    private String msg;

    public ApiException() {}

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiException(Throwable e) {
        super(e);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
