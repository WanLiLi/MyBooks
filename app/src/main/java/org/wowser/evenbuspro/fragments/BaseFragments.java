package org.wowser.evenbuspro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wowser.evenbuspro.BusProvider;
import org.wowser.evenbuspro.utils.MLog;

/**
 * Created by Wowser on 2016/3/16.
 */
public abstract class BaseFragments extends Fragment{

    void  initView(View view){}

    @Override
    public void onAttach(Context context) {
        MLog.d("life","BaseFragments-onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MLog.d("life","BaseFragments-onCreate");
        super.onCreate(savedInstanceState);
        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 只执行一次，除非销毁此fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MLog.d("life","BaseFragments-onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       MLog.d("life","BaseFragments-onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MLog.d("life","BaseFragments-onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        MLog.d("life","BaseFragments-onStart");
        super.onStart();

    }

    @Override
    public void onResume() {
        MLog.d("life","BaseFragments-onResume");
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onPause() {
        MLog.d("life","BaseFragments-onPause");
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Override
    public void onStop() {
        MLog.d("life","BaseFragments-onStop");
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        MLog.d("life","BaseFragments-onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        MLog.d("life","BaseFragments-onDestroy");
        super.onDestroy();
    }
}
