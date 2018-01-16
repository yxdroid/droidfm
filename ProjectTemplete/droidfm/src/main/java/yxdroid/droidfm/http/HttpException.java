package yxdroid.droidfm.http;

public class HttpException extends RuntimeException {

    public HttpException(Throwable t) {
        super(t);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
