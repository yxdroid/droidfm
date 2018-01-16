package yxdroid.pt.model.impl;

import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.mvp.model.BaseModel;
import yxdroid.pt.api.ICommonApi;
import yxdroid.pt.bean.Token;
import yxdroid.pt.model.ICommonModel;

public class CommonModelImpl extends BaseModel implements ICommonModel {

    @Override
    public void getToken(HttpCallback<Token> callback, String clientStr) {
        addHeader("Clientstr", clientStr);
        doHttpRequest(getApiService(ICommonApi.class).getToken(), callback);
    }
}
