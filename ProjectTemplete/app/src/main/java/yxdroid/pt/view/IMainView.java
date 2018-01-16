package yxdroid.pt.view;

import yxdroid.droidfm.http.ApiException;
import yxdroid.droidfm.mvp.view.IBaseView;

public interface IMainView extends IBaseView {

    void onLoginSuccess();

    void onLoginFailure(ApiException exception);
}
