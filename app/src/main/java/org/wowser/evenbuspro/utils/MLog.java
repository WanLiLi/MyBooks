package org.wowser.evenbuspro.utils;

import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: SZY
 * Date: 15/5/12
 * Time: 上午11:10
 */
public class MLog {

    private static final String TAG = "escort";
//    private static final boolean SHOW = BuildConfig.DEBUG;
//    private static final boolean SHOW = true;

    public static void d(String msg) {
//        if(!SHOW) return;
        Log.d(TAG, msg);
    }
    public static void d(String tag,String msg) {
//        if(!SHOW) return;
        Log.d(tag, msg);
    }
}
