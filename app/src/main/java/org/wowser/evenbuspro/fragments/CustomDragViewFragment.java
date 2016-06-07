package org.wowser.evenbuspro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro._customview.CustomDragView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wanli on 2016/5/3.
 */
public class CustomDragViewFragment extends BaseFragments {

    public static String TAG = "CustomDragViewFragment";
    @Bind(R.id.custom_dragView)
    CustomDragView customDragView;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.custom_drag_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
