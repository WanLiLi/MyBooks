package org.wowser.evenbuspro.test_class;

import android.os.Handler;

import org.wowser.evenbuspro.utils.MLog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanli on 2016/5/19.
 */
public class Li {
    /**
     * 相当于B类有参数为CallBack callBack的f()---->背景三
     * @param callBack
     * @param question  小王问的问题
     */
    public void executeMessage(CallBackInter callBack, String question){
        MLog.d("wanli_callback", "小王问的问题--->" + question);
        //模拟小李办自己的事情需要很长时间
        //问题：遍历数字太多，后面执行不了
//        for(int i=1; i<=9999;i++){
//            MLog.d("wanli_callback", i+"");
//        }

//休眠后再执行下面的代码
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /**
         * 先执行后面的代码
         * */
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                MLog.d("wanli_callback", "延迟5秒后发送");
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(timerTask,5000);




        /**
         * 小李办完自己的事情之后想到了答案是2
         */
        String result = "答案是2";

        /**
         * 于是就打电话告诉小王，调用小王中的方法
         * 这就相当于B类反过来调用A的方法D
         */
        callBack.solve(result);
    }

}
