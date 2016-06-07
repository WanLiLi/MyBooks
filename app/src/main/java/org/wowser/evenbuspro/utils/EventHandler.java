package org.wowser.evenbuspro.utils;


import android.os.Handler;

/**
 * Created by Wowser on 2016/3/18.
 */
public    class  EventHandler {
    private Handler mHandler;
    private static EventHandler eventHandler;


    public static  EventHandler getInstance(){
        if(eventHandler==null){
            eventHandler = new EventHandler();
        }
        return eventHandler;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public  void removeHandler(){ mHandler = null; }


    public  void removeHandler(int what){
        mHandler.removeMessages(what);
    }






}
