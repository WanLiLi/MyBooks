package org.wowser.evenbuspro.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.model.BookModel;
import org.wowser.evenbuspro.model.Books;
import org.wowser.evenbuspro.utils.MLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanli on 2016/5/24.
 */
public class RecycGalleryAdapter extends RecyclerView.Adapter<RecycGalleryAdapter.ViewHolderGallery> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private List<BookModel> dataList = new ArrayList<BookModel>();

    public RecycGalleryAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    private int inflateContentView() {
        return R.layout.item_gallery;
    }


    @Override
    public ViewHolderGallery onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(inflateContentView(), parent, false);
        return new ViewHolderGallery(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderGallery holder, final int position) {
        holder.position = position;
        holder.txtGallery.setText(position + "");
        //holder.txtGallery.setText(dataList.get(position).getPrice());
        Glide.with(mContext).load(dataList.get(position).getImage()).placeholder(R.drawable.ic_person).override(128, 128).into(holder.imageGallery);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /**
     * 单击监听事件
     */
    public interface onItemClickListenerByGallery {
        void onItemClick(View view, int position);
    }

    private static onItemClickListenerByGallery mItemListener;

    public void setOnItemClickListener(onItemClickListenerByGallery onItemClickListener) {
        this.mItemListener = onItemClickListener;
    }


    static class ViewHolderGallery extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.image_gallery)
        ImageView imageGallery;
        @Bind(R.id.txt_gallery)
        TextView txtGallery;

        @Bind(R.id.lay_item_gallery)
        LinearLayout layGallery;

        public int position;

        ViewHolderGallery(View view) {
            super(view);
            ButterKnife.bind(this, view);
            layGallery.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lay_item_gallery:
                    if (mItemListener != null) {
                        mItemListener.onItemClick(v, position);
                    }
                    break;
            }
        }
    }


    public void loadDataFromNet(String bookType) {
        OkHttpClient ohc = new OkHttpClient();
        String URL = "https://api.douban.com/v2/book/search?q=" + bookType + "&fields=title,author,image,price,publisher";
        Request request = new Request.Builder().url(URL).build();

        ohc.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                MLog.d("json:" + json);
                Gson gson = new Gson();
                final Books model = gson.fromJson(json, Books.class);
                if (model == null) {
                    return;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (dataList != null) {
                            dataList.clear();
                        }
                        dataList.addAll(model.getBooks());
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }


}
