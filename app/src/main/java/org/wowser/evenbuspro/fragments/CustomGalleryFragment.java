package org.wowser.evenbuspro.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro._customview.customGalleryRecyc;
import org.wowser.evenbuspro.adapter.RecycGalleryAdapter;
import org.wowser.evenbuspro.tool.BitmapUtil;
import org.wowser.evenbuspro.utils.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomGalleryFragment extends Fragment implements RecycGalleryAdapter.onItemClickListenerByGallery,customGalleryRecyc.OnItemScrollChangeListener{

    public static String TAG = "CustomGalleryFragment";
    @Bind(R.id.image_large)
    ImageView imageLarge;
    @Bind(R.id.recyc_Gallery)
    customGalleryRecyc recycGallery;

    private RecycGalleryAdapter adapter;

    public static CustomGalleryFragment newInstance() {
        return new CustomGalleryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_gallery, container, false);
        ButterKnife.bind(this, view);

        initView();
        initEvent();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearMa = new LinearLayoutManager(getActivity());
        linearMa.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycGallery.setLayoutManager(linearMa);


        adapter = new RecycGalleryAdapter(getActivity());
        adapter.loadDataFromNet("html5");
        recycGallery.setAdapter(adapter);
    }

    private void initEvent() {
        adapter.setOnItemClickListener(this);

        recycGallery.setOnItemScrollChangeListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image_gallery);
        Bitmap bitmap = BitmapUtil.createDraggingItemImage(imageView, null);
        imageLarge.setImageBitmap(bitmap);
        Toast.makeText(getActivity(),""+position,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onChange(View view, int position) {
        Bitmap bitmap = BitmapUtil.getViewBitmap(view);
        imageLarge.setImageBitmap(bitmap);
    }
}
