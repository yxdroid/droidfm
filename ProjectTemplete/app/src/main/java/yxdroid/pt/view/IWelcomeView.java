package yxdroid.pt.view;

import yxdroid.droidfm.mvp.view.IBaseView;
import yxdroid.pt.bean.Token;

public interface IWelcomeView extends IBaseView {

    void onGetTokenSuccess(Token token);
}
