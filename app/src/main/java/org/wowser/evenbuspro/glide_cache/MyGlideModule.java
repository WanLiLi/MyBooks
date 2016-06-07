package org.wowser.evenbuspro.glide_cache;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Wowser on 2016/3/24.
 * https://github.com/bumptech/glide/wiki/Configuration
 */
public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDiskCache(
//                new InternalCacheDiskCacheFactory(context,cacheDirectoryName, yourSizeInBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
