package org.wowser.evenbuspro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.kymjs.kjframe.KJBitmap;
import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.model.Book;
import org.wowser.evenbuspro.model.BookModel;
import org.wowser.evenbuspro.model.Books;
import org.wowser.evenbuspro.utils.GsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.Looper.getMainLooper;

/**
 * Created by Wowser on 2016/3/18.
 */
public class ListAdapter_book extends BaseAdapter  {
    private Context context;
    private List<BookModel> bookList = new ArrayList<BookModel>();
    private LayoutInflater mInflater;

    public  OnRefreshingListener mOnRefreshingListener;
    public  interface  OnRefreshingListener{
        public void  setRefreshing();
    }

    public  void  setOnRefreshingListener(OnRefreshingListener onRefreshingListener)
    {
        this.mOnRefreshingListener = onRefreshingListener;
    }


    public void LoadBookFromNet(String tag) {
        OkHttpClient client = new OkHttpClient();
             Request request = new Request.Builder()
                .url("https://api.douban.com/v2/book/search?q=" + tag + "&fields=title,author,image,price,publisher")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(getMainLooper()).post(new Runnable() {
                    public void run() {
                        //网络失败
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功（也可能失败） 先不做错误的处理
                //Log.d("wanli",response.body().string());

                String json = response.body().string();
                Gson gson = new Gson();
                final Books books = gson.fromJson(json, Books.class);
                if (books == null) {
                    return;
                }
                bookList.clear();
                bookList.addAll(books.getBooks());
                Collections.sort(bookList);  //价格排序
                new Handler(getMainLooper()).post(new Runnable() {
                    public void run() {
                        //不能在子线程中操作UI主线程，所以开启一个主线程
                        mOnRefreshingListener.setRefreshing();   //刷新也是一样的，必须在主线程
                        notifyDataSetChanged();
                    }
                });
//                for (Books.BookModel list : model.getBooks()){
//                    list.getImage();
//                    list.getTitle();
//                    list.getPublisher();
//                    list.getPrice();
//                    list.getAuthor();
//                }

            }
        });
    }

    public ListAdapter_book(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_one, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.imgPhoto);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPress = (TextView) convertView.findViewById(R.id.txtPress);
            holder.txtAuthor = (TextView) convertView.findViewById(R.id.txtAuthor);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            holder.txtPurchase = (TextView) convertView.findViewById(R.id.txtPurchase); //购买

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imagPath = bookList.get(position).getImage();
//        KJBitmap kjBitmap = new KJBitmap();
//        kjBitmap.displayCacheOrDefult(holder.imageView,imagPath,R.drawable.ic_person);

        Glide.with(context).load(imagPath).placeholder(R.drawable.ic_person).into(holder.imageView);  //glide加载图片

        String txtName = String.format(context.getResources().getString(R.string.txt_Press), bookList.get(position).getTitle());
        String txtPress = String.format(context.getResources().getString(R.string.txt_Press), bookList.get(position).getPublisher());
        String txtPrice = String.format(context.getResources().getString(R.string.txt_Price), bookList.get(position).getPrice());


        holder.txtName.setText(txtName);  //书名
        holder.txtPress.setText(txtPress);//出版社
        holder.txtPrice.setText(txtPrice); //价格

        //作者
        String author;
        StringBuffer stringBuffer = new StringBuffer();
        List<String> authorList = bookList.get(position).getAuthor();
        for (int i = 0; i < authorList.size(); i++) {
            author = bookList.get(position).getAuthor().get(i);
            stringBuffer.append(author + " ");
        }
        String txtAuthor = String.format(context.getResources().getString(R.string.txt_Author), stringBuffer.toString());
        holder.txtAuthor.setText(txtAuthor);


        holder.txtPurchase.setText(R.string.txt_Purchase);  //购买
        return convertView;
    }


    static class ViewHolder {
        private ImageView imageView;
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtPress;
        private TextView txtAuthor;

        private TextView txtPurchase;
    }
}
