package yxdroid.pt.model.impl;

import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.http.Params;
import yxdroid.droidfm.mvp.model.BaseModel;
import yxdroid.pt.api.IUserApi;
import yxdroid.pt.model.IMainModel;


public class MainModelImpl extends BaseModel implements IMainModel {

    @Override
    public void userLogin(HttpCallback<String> callback, Params params) {
        doHttpRequest(getApiService(IUserApi.class).userLogin(params), callback);
    }
}
