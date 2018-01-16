package yxdroid.droidfm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yxdroid.droidfm.mvp.view.IBaseView;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements IBaseView {

    protected View rootView;

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
}
