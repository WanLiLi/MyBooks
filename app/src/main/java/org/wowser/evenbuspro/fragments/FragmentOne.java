package org.wowser.evenbuspro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.adapter.ListAdapter_book;

/**
 * Created by Wowser on 2016/3/16.
 */
public class FragmentOne extends BaseFragments implements ListAdapter_book.OnRefreshingListener {
    //private ImageView imageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView  list_book;


    private  ListAdapter_book adapter_book;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
              getUserVisibleHint();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.onefrag,null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    void  initView(View view) {
        super.initView(view);
        //imageView = (ImageView) view.findViewById(R.id.imgone);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.red,R.color.heise,R.color.yellow,R.color.colorPrimary);
        list_book = (ListView) view.findViewById(R.id.list_book);
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        }, 100);

        adapter_book = new ListAdapter_book(getActivity());
        adapter_book.setOnRefreshingListener(this);  //register
    }

    void initData(){
        list_book.setAdapter(adapter_book);
        adapter_book.LoadBookFromNet("android");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        adapter_book.LoadBookFromNet("PHP");
                    }
                }, 1500);
            }
        });
    }




    //响应otto
    @Subscribe
    public void changeImgColor(Integer colorId){
        //imageView.setBackgroundResource(R.color.colorAccent);
    }

    @Override
    public void setRefreshing() {
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
