package yxdroid.pt.presenter;

import android.content.Context;

import yxdroid.droidfm.http.ApiException;
import yxdroid.droidfm.http.HttpCallback;
import yxdroid.droidfm.mvp.model.BaseModel;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.droidfm.utils.DeviceUtil;
import yxdroid.pt.activity.WelcomeActivity;
import yxdroid.pt.bean.Token;
import yxdroid.pt.model.ICommonModel;
import yxdroid.pt.model.impl.CommonModelImpl;
import yxdroid.pt.view.IWelcomeView;

public class WelcomePresenter extends BasePresenter<IWelcomeView, WelcomeActivity> {

    private ICommonModel commonModel;

    public WelcomePresenter() {
        commonModel = new CommonModelImpl();
    }

    public void initToken(Context context) {
        if (isAttachView()) {

            commonModel.getToken(new HttpCallback<Token>(getActivity(), getTag()) {

                @Override
                public void onStart() {
                    getView().onShowLoading();
                }

                @Override
                public void onSuccess(Token token) {
                    ((BaseModel) commonModel).addHeader("Token-Key", token.getTokenKey());
                    ((BaseModel) commonModel).addHeader("Token-Value", token.getTokenValue());
                    ((BaseModel) commonModel).addHeader("Channel", "vrseen");

                    getView().onGetTokenSuccess(token);
                }

                @Override
                public void onFailure(ApiException ex) {
                    getActivity().showTip(ex.getMsg());
                }

                @Override
                public void onComplete() {
                    getView().onCloseLoading();
                }
            }, DeviceUtil.getDeviceId(context));

        }
    }
}
