package org.wowser.evenbuspro.cache.netcache;

import android.content.Context;

import org.wowser.evenbuspro.cache.ListCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SZY
 * Date: 15/6/9
 * Time: 下午6:25
 */
public class ListCacheFactory<T> {

    private File cacheFile;
    private ListCache<T> listCache;


    public ListCacheFactory(Context context, String fileName) {
        File dir = context.getFilesDir();  //data/data/com.mbox.cn/files
        if(!dir.exists()) dir.mkdir();
        File eidDir = new File(dir + File.separator );
        if(!eidDir.exists()) eidDir.mkdir();  //data/data/com.mbox.cn/files/
        String path = eidDir.getAbsolutePath() + File.separator + fileName; //data/data/com.mbox.cn/files/ fileName
        cacheFile = new File(path);
        if(!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listCache = new ListCache<T>();
        listCache.setPath(path);       //保存文件路径
    }

    public void cacheList(List<T> list) {
        listCache.putCacheData(list);
    }

    public List<T> getList() {
        return listCache.getCacheData();
    }

    public void cacheObjectOfList(T t) {
        //先读取然后在缓存
        List<T> list = listCache.getCacheData();
        if(list == null) list = new ArrayList<T>();
        list.add(t);
        listCache.putCacheData(list);
    }

    public void removeObjectOfList(int position) {
        List<T> list = listCache.getCacheData();
        if(list == null || list.size() == 0) return;
        list.remove(position);
        listCache.putCacheData(list);
    }
}
