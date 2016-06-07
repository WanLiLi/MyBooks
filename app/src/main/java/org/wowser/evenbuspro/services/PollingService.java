package org.wowser.evenbuspro.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.activitys.LoginActivity;
import org.wowser.evenbuspro.activitys.MainActivity;

/**
 * Created by wanli on 2016/4/4.
 * 如果用 bindService 这种方式调用，onStart和onStartCommand都不会被调用到
 * 如果是用StartService调用，两个都被调用到
 *
 *  onStartComand使用时，返回的是一个(int)整形。
 * 1):START_STICKY： 如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由 于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传 递到service，那么参数Intent将为null。
 * 2):START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务
 * 3):START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
 * 4):START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
 */
public class PollingService extends Service {
    public static final String ACTION = "com.ryantang.service.PollingService";

    private Notification mNotification;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("onCreate");
        initNotifiManager();
    }

    @Override
    public ComponentName startService(Intent service) {
        System.out.println("startService");
        //new PollingThread().start();
        return super.startService(service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        new PollingThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("onStart");
    }


    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        // 通过Notification.Builder来创建通知，注意API Level
        // API11之后才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_person) //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                    .setTicker("TickerText:" + "您有新短消息，请注意查收！")  //设置提示文字
                    //设置标题上显示的提示文字
                    .setContentTitle("Notification Title")//
                    //内容文字
                    .setContentText("This is the notification message")
                    //
                    .setContentIntent(pendingIntent)
                    .setNumber(1) //在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                    .build();
            //.getNotification(); // 需要注意build()是在API level16及之后增加的，在API11中可以使用getNotificatin()来代替
        }
        //FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotification.when = System.currentTimeMillis();
    }

    int id = 1;

    //弹出Notification
    private void showNotification() {
        mManager.notify(1, mNotification);
    }


    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     *
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;

    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count++;
            //当计数能被5整除时弹出通知
            //if (count % 5 == 0) {
            showNotification();
            System.out.println("New message!");
            //}
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("Service:onDestroy");
        super.onDestroy();
    }


}
