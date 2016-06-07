package org.wowser.evenbuspro.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.adapter.RecyclerViewFour;
import org.wowser.evenbuspro.preferencesUtil.pp.PreferencesFactory;

/**
 * Created by Wowser on 2016/3/16.
 */
public class FragmentFour extends BaseFragments {
    private ImageView imageView;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fourfrag,null);
        imageView = (ImageView) view.findViewById(R.id.imgFour);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_three);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    public void initData(){
        PreferencesFactory factory = new PreferencesFactory(getActivity());
        boolean isfirst =  factory.getVisitNet();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(), 2);
//        mRecyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewFour adapter = new RecyclerViewFour(getActivity());
        mRecyclerView.setAdapter(adapter);

        if(isfirst){
            adapter.loadDataFromNet("android");
            factory.setVisitNet(false);
        }else{
            adapter.loadSetDataFromCache();
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Subscribe
    public void changeImgColor(Integer colorId){
        imageView.setBackgroundResource(R.color.colorAccent);
    }

}
