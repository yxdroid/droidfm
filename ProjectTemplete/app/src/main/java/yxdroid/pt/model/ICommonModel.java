package yxdroid.pt.model;

import yxdroid.droidfm.http.HttpCallback;
import yxdroid.pt.bean.Token;

public interface ICommonModel {

    void getToken(HttpCallback<Token> callback, String clientStr);
}
