package org.wowser.evenbuspro.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.cache.CacheConfig;
import org.wowser.evenbuspro.cache.ListCache;
import org.wowser.evenbuspro.cache.netcache.ListCacheFactory;
import org.wowser.evenbuspro.model.BookModel;
import org.wowser.evenbuspro.model.Books;
import org.wowser.evenbuspro.preferencesUtil.pp.PreferencesFactory;
import org.wowser.evenbuspro.utils.MLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wowser on 2016/3/30.
 */
public class RecyclerViewFour extends RecyclerView.Adapter<RecyclerViewFour.MyViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<BookModel> dataList = Collections.synchronizedList(new ArrayList<BookModel>());

    public RecyclerViewFour(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    private ListCacheFactory factoryDataList;
    private ListCacheFactory factory;  //设置后的数据

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_item_four, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookModel book = dataList.get(position);
        if (book.getType() == 0) {
            //第一次进来设置为下标
            if (flagOne == -1) {
                flagOne = position;  //flagOne = 0
            }
            //
            if(flagOne==position){
                holder.txtType.setVisibility(View.VISIBLE);
                holder.txtType.setText("买进");
            }else{
                holder.txtType.setVisibility(View.GONE);
            }
        } else if (book.getType() == 1) {
            if (flagTwo == -1) {
                flagTwo = position;
            }
            if(flagTwo==position){
                holder.txtType.setVisibility(View.VISIBLE);
                holder.txtType.setText("卖出");
            }else{
                holder.txtType.setVisibility(View.GONE);
            }
        }

        Glide.with(mContext).load(dataList.get(position).getImage()).placeholder(R.drawable.ic_person).override(128, 128).into(holder.imageView);
        //holder.txtType.setText((Integer.valueOf(dataList.get(position).getType()) == 0 ? "买进"  : "卖出"));
        holder.txtName.setText(dataList.get(position).getTitle());
        holder.txtPress.setText(dataList.get(position).getPublisher());
        holder.txtPrice.setText(dataList.get(position).getPrice());
        ArrayList<String> authorLists = (ArrayList<String>) dataList.get(position).getAuthor();
        StringBuffer buffer = new StringBuffer();
        for (String tuthor : authorLists) {
            buffer.append(tuthor);
        }
        holder.txtAuthor.setText(buffer);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /**
     * 决定元素的布局用使哪种类型
     *
     * @param position 数据源的下标
     * @return 一个int型标志，传递给 onCreateViewHolder 的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtPress;
        private TextView txtAuthor;

        private TextView txtType;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgPhoto);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtPress = (TextView) itemView.findViewById(R.id.txtPress);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
        }

    }


    public void cachaDataList(List<BookModel> dataList) {
        if (factoryDataList == null) {
            factoryDataList = new ListCacheFactory(mContext, CacheConfig.BOOK_INFO_FOUR_ORIGINAL);
        }
        factoryDataList.cacheList(dataList);
    }


    public List<BookModel> getCachaDataList() {
        if (factoryDataList == null) {
            factoryDataList = new ListCacheFactory(mContext, CacheConfig.BOOK_INFO_FOUR_ORIGINAL);
        }
        return factoryDataList.getList();
    }


    public void loadSetDataFromCache() {
        List<BookModel> cachaDataList_original = getCachaDataList();
        List<BookModel> cachaDataList_setType = new ArrayList<BookModel>();

        for (int i = 0; i < cachaDataList_original.size(); i++) {
            if (i % 2 != 0) {
                cachaDataList_original.get(i).setType(1);
            }
        }
        cachaDataList_setType.clear();
        cachaDataList_setType.addAll(cachaDataList_original);
        SortDataList(cachaDataList_setType);
    }


    int flagOne = -1;
    int flagTwo = -1;

    public void SortDataList(List<BookModel> cachaDataList_setType) {
        List<BookModel> list = new ArrayList<BookModel>();
        List<String> visiableList = new ArrayList<String>();

        for (int i = 0; i < cachaDataList_setType.size(); i++) {
            int type = cachaDataList_setType.get(i).getType();
            if (type == 0) {
//                if(flagOne == 1){
//                    flagOne=2;
//                    cachaDataList_setType.get(i).setVisibleFlag(true);
//                }
                list.add(cachaDataList_setType.get(i));
            }
        }
        for (int i = 0; i < cachaDataList_setType.size(); i++) {
            int type = cachaDataList_setType.get(i).getType();
            if (type == 1) {
                list.add(cachaDataList_setType.get(i));
            }
        }
        if (dataList != null)
            dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
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
                if (dataList != null) {
                    dataList.clear();
                }
                dataList.addAll(model.getBooks());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                        Toast.makeText(mContext,"ggggggggggg",Toast.LENGTH_LONG).show();
                    }
                });
                MLog.d("sssssssss","重新回到111");
                cachaDataList(dataList);
                MLog.d("sssssssss","重新回到222");
            }
        });
    }


}
