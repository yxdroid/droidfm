package yxdroid.pt.presenter;

import yxdroid.droidfm.http.ApiException;
import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.http.Params;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.pt.activity.MainActivity;
import yxdroid.pt.model.IMainModel;
import yxdroid.pt.model.impl.MainModelImpl;
import yxdroid.pt.view.IMainView;

public class MainPresenter extends BasePresenter<IMainView, MainActivity> {

    private IMainModel mainModel;

    public MainPresenter() {
        mainModel = new MainModelImpl();
    }

    public void login() {
        if (isAttachView()) {

            Params params = new Params();
            params.put("account", "15858140509");
            params.put("password", "123456");
            params.put("registrationId", System.currentTimeMillis() + "");

            mainModel.userLogin(new HttpCallback<String>(getActivity(), getTag()) {

                @Override
                public void onStart() {
                    getView().onShowLoading();
                }

                @Override
                public void onSuccess(String s) {
                    getView().onLoginSuccess();
                }

                @Override
                public void onFailure(ApiException ex) {
                    getView().onLoginFailure(ex);
                }

                @Override
                public void onComplete() {
                    getView().onCloseLoading();
                }
            }, params);
        }
    }
}
