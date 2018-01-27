package yxdroid.pt.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yxdroid.droidfm.base.BaseFragment;
import yxdroid.pt.R;

public class Fragment4 extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_4, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
