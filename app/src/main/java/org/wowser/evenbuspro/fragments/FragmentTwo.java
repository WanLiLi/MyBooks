package org.wowser.evenbuspro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.adapter.RecyclerAdapter;
import org.wowser.evenbuspro.cache.CacheConfig;
import org.wowser.evenbuspro.cache.netcache.ListCacheFactory;
import org.wowser.evenbuspro.model.BookModel;
import org.wowser.evenbuspro.utils.MLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Wowser on 2016/3/16.
 */
public class FragmentTwo extends BaseFragments {
    private ImageView imageView;
    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    private ListCacheFactory<BookModel> listCacheFactory;
    List<BookModel> mBookModels = new ArrayList<BookModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.twofra, null);
        MLog.d("life","FragmentTwo-onCreateView");

        imageView = (ImageView) view.findViewById(R.id.imgTwo);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_two);

        initData();
        initEvent();
        return view;
    }

    private void initEvent() {

        /**
         * 加载到底部
         * */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                MLog.d("loadMore: ", " dx" + dx + "  dy:" + dy);


                //获取看见的完整item的positon
                int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                MLog.d("loadMore: ", " lastPosition: " + lastPosition);

                //如果是最后一个
                if (adapter.getItemCount() == lastPosition + 1 && dy>0) {
                    MLog.d("loadMore: ", " 更多");
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        MLog.d("life","FragmentTwo-onCreateView");
    }


    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));  //LinearLayoutManager(getActivity())
        adapter = new RecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        // 设置item动画
        //recyclerView.setItemAnimator(new DefaultItemAnimator());


        // List<BookModel> bookModels =   getCacheSwapBooks();
        // if(bookModels != null && bookModels.size()>0){
        //     adapter.setDataListAndNotiFy(bookModels);
        //     adapter.notifyDataSetChanged();
        // }else{
        adapter.loadDataFromNet("android");
        //}
        initMovement();
    }


    private void initMovement() {
        final List<BookModel> bookModelList = adapter.getDataList();
        //1、dragDirs- 表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
        //2、swipeDirs- 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT) {
            /**
             * @param recyclerView
             * @param viewHolder   拖动的ViewHolder
             * @param target       目标位置的ViewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(bookModelList, i, i + 1);
                    }
//                    Collections.swap(bookModelList,fromPosition,toPosition);
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(bookModelList, i, i - 1);
                    }
//                    Collections.swap(bookModelList,fromPosition,toPosition);
                }
                for (BookModel bmodel : bookModelList) {
                    MLog.d("Position：", "fromPosition: " + fromPosition + "toPosition: " + toPosition);
                }
                adapter.notifyItemMoved(fromPosition, toPosition);

                //mBookModels=bookModelList;
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                bookModelList.remove(position);
                adapter.notifyItemRemoved(position);
            }

        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }


    public void CacheSwapBooks(List<BookModel> bookModelList) {
        if (listCacheFactory == null) {
            listCacheFactory = new ListCacheFactory(getActivity(), CacheConfig.SWAP_ITEM_LIST);
        }
        listCacheFactory.cacheList(bookModelList);
    }


    public List<BookModel> getCacheSwapBooks() {
        if (listCacheFactory == null) {
            listCacheFactory = new ListCacheFactory(getActivity(), CacheConfig.SWAP_ITEM_LIST);
        }
        return listCacheFactory.getList();
    }

    @Override
    public void onPause() {
        MLog.d("life","FragmentTwo-onCreateView");
        super.onPause();
        CacheSwapBooks(mBookModels);

    }

    @Override
    public void onStop() {
        super.onStop();
        MLog.d("life","FragmentTwo-onCreateView");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MLog.d("life","FragmentTwo-onCreateView");
    }


    @Subscribe
    public void changeImgColor(Integer colorId) {
        imageView.setBackgroundResource(R.color.red);
    }


}
