package org.wowser.evenbuspro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.adapter.DragAdapter;
import org.wowser.evenbuspro._customview.DragGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by
 */
public class FragmentThree extends BaseFragments {
    private ImageView imageView;
    private DragGridView mDragGridView;

    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.threefrag, null);
        imageView = (ImageView) view.findViewById(R.id.imgThree);
        mDragGridView = (DragGridView) view.findViewById(R.id.dragGridView);
        initDragView();
        return view;
    }

    public void initDragView() {
        for (int i = 0; i < 15; i++) {
            HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
            itemHashMap.put("item_image", R.drawable.ic_person);
            itemHashMap.put("item_text", "sss" + Integer.toString(i));
            dataSourceList.add(itemHashMap);
        }
        final DragAdapter mDragAdapter = new DragAdapter(getActivity(), dataSourceList);
        mDragGridView.setAdapter(mDragAdapter);

        mDragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mDragGridView.removeItemAnimation(position);
            }
        });
    }


    @Subscribe
    public void changeImgColor(Integer colorId) {
        imageView.setBackgroundResource(R.color.colorAccent);
    }


}
