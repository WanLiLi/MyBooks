package org.wowser.evenbuspro.adapter;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.map.MyLocationData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.model.Book;
import org.wowser.evenbuspro.model.BookModel;
import org.wowser.evenbuspro.model.Books;
import org.wowser.evenbuspro.utils.MLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wanli on 2016/3/26.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<BookModel> dataList = new ArrayList<BookModel>();


    public void setDataListAndNotiFy(List<BookModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<BookModel> getDataList() {
        if (dataList == null) {
            return null;
        }
        return this.dataList;
    }


    public RecyclerAdapter(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.position = position;
        Glide.with(mContext).load(dataList.get(position).getImage()).placeholder(R.drawable.ic_person).override(128, 128).into(holder.imageView);

        holder.layItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_LONG).show();
                MLog.d("onBindViewHolder", "onClick");
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public CardView layItem;
        public int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
            layItem = (CardView) itemView.findViewById(R.id.lay_recycItem_two);

            layItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lay_recycItem_two:
                    MLog.d("MyViewHolder", "onClick");
                    break;
            }
        }
    }


    public void loadDataFromNet(String bookType) {
        ///OkHttpClient ohc = new OkHttpClient();
        String URL = "https://api.douban.com/v2/book/search?q=" + bookType + "&fields=title,author,image,price,publisher";

        //以上就是发送一个get请求的步骤，首先构造一个Request对象，参数最起码有个url，当然你可以通过Request.Builder设置更多的参数比如：header、method等。
        //Request request = new Request.Builder().url(URL).build();

        //然后通过request的对象去构造得到一个Call对象，类似于将你的请求封装成了任务，既然是任务，就会有cal.execute()和cancel()等方法。
        /// Call cal = ohc.newCall(request);


        //最后，我们希望以异步的方式去执行请求，所以我们调用的是call.enqueue，将call加入调度队列，然后等待任务执行完成，我们在Callback中即可得到结果。
//        cal.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            //onResponse回调的参数是response，一般情况下，比如我们希望获得返回的字符串，可以通过response.body().string()获取；
//            // 如果希望获得返回的二进制字节数组，则调用
//            // response.body().bytes()；
//            // 如果你想拿到返回的inputStream，则调用response.body().byteStream()
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//
//                String json = response.body().string();
//                MLog.d("json:" + json);
//                Gson gson = new Gson();
//                final Books model = gson.fromJson(json, Books.class);
//                if (model == null) {
//                    return;
//                }
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (dataList != null) {
//                            dataList.clear();
//                        }
//                        dataList.addAll(model.getBooks());
//                        notifyDataSetChanged();
//                    }
//                });
//            }


        //看到这，你可能会奇怪，竟然还能拿到返回的inputStream，看到这个最起码能意识到一点，这里支持大文件下载，有inputStream我们就可以通过IO的方式写文件。
        // 不过也说明一个问题，这个onResponse执行的线程并不是UI线程。的确是的，如果你希望操作控件，还是需要使用handler等，例如：
//            @Override
//            public void onResponse(final Response response) throws IOException
//            {
//                final String res = response.body().string();
//                runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        mTv.setText(res);
//                    }
//
//                });
//            }
        //    });

        OkHttpUtils.get(URL)     // 请求方式和请求url
                .tag("haha")                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        MLog.d("onResponse", "s:" + s);
                        MLog.d("onResponse", "request:" + request);

                        Gson gson = new Gson();
                        final Books model = gson.fromJson(s, Books.class);
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


    //HTTP POST
    //POST提交Json数据
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    //post 表单提交   FormEncodingBuilder 3.0修改为 FormBody
    public String post2(String url, String json) throws IOException {

        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("platform", "your_value_1")
                .add("your_param_2", "your_value_2")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }


    //（三）基于Http的文件上传
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        File file = new File("README.md");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

}
