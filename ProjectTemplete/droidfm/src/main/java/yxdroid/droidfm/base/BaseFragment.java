package yxdroid.droidfm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import yxdroid.droidfm.fragment.FragmentCommunication;
import yxdroid.droidfm.mvp.view.IBaseView;

public abstract class BaseFragment extends Fragment implements IBaseView {

    protected View rootView;

    protected FragmentCommunication fragmentCommunication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseMVPActivity) {
            fragmentCommunication = FragmentCommunication.getInstance();
            ((BaseMVPActivity)context).setFragmentCommunicationInterface(getTag());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onShowLoading() {
        ((BaseMVPActivity) getActivity()).onShowLoading();
    }

    @Override
    public void onCloseLoading() {
        ((BaseMVPActivity) getActivity()).onCloseLoading();
    }

    public void showTip(String text) {
        ((BaseMVPActivity) getActivity()).showTip(text);
    }

    public void showTip(int resId) {
        ((BaseMVPActivity) getActivity()).showTip(resId);
    }
}
