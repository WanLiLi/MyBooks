package org.wowser.evenbuspro.preferencesUtil.pp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Wowser on 2016/3/24.
 */
public class Loginpreferences {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Loginpreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("cishu", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public int getLoginCacheCishu() {
        return sharedPreferences.getInt("cishu", 0);
    }

    public void setLoginCacheCishu(int cishu) {
        editor.putInt("cishu", cishu).commit();
    }
}
