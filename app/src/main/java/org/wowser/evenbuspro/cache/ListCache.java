package org.wowser.evenbuspro.cache;


import org.wowser.evenbuspro.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SZY
 * Date: 15/5/14
 * Time: 下午5:29
 */
public class ListCache<M> implements IFileCache<List<M>> {

    private String path;

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean putCacheData(List<M> list) {
        FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                File cacheFile = new File(path);
                MLog.d("wanli","ListCache"+"-putCacheData-文件路径："+cacheFile.getAbsolutePath());
                try {
                    fos = new FileOutputStream(cacheFile, false);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(list);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                if(fos != null)
                    fos.close();
                if(oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<M> getCacheData() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<M> list = null;
        try {
            File cacheFile = new File(path);
            if(cacheFile.length() > 0) {
                fis = new FileInputStream(cacheFile);
                ois = new ObjectInputStream(fis);
                list = (List<M>) ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null)
                    fis.close();
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
