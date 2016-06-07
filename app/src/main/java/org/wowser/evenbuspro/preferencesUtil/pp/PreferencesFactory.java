package org.wowser.evenbuspro.preferencesUtil.pp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wowser on 2016/4/1.
 */
public class PreferencesFactory {

    private static  String SHARE_PARAMS = "share_params";


    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    public PreferencesFactory(Context context) {
       sp =  context.getSharedPreferences(SHARE_PARAMS,Context.MODE_PRIVATE);
       editor =  sp.edit();
    }



    public void setVisitNet(boolean flag){
        editor.putBoolean("isFirstNet",flag);
        editor.commit();
    }



    public boolean getVisitNet(){
        return  sp.getBoolean("isFirstNet",true);
    }

}
