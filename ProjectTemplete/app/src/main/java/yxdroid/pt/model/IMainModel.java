package yxdroid.pt.model;

import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.http.Params;

public interface IMainModel {

    void userLogin(HttpCallback<String> callback, Params params);
}
