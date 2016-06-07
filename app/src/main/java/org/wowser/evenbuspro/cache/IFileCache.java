package org.wowser.evenbuspro.cache;

/**
 */
public interface IFileCache<T> {
    public void setPath(String path);
    public boolean putCacheData(T t);
    public T getCacheData();
}
