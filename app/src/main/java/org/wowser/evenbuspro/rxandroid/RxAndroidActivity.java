package org.wowser.evenbuspro.rxandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.utils.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。
 * RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
 * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。需要注意的是，
 * onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
 */
public class RxAndroidActivity extends AppCompatActivity {
    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.image_1)
    ImageView image1;
    @Bind(R.id.widget108)
    ProgressBar widget108;


    private String TAG = "RxAndroidActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        ButterKnife.bind(this);
    }

    private void observableSimple() {
        /**
         * 一：
         *      创建 观察者（仆人）  Observer
         *      以下包含逐一讲解简化步骤
         * */

        /**
         * 1：Observer
         * */
//        Observer<String> subscriber = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                MLog.d(TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MLog.d(TAG, "onError: "+e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                MLog.d(TAG, "onNext: "+s);
//            }
//        };


        /**
         *
         * 2:    除了 Observer 接口之外，RxJava 还内置了一个 实现了Observer的 抽象类：Subscriber。
         *       Subscriber 对 Observer 接口进行了一些 扩展。
         *       但他们的基本使用方式是完全一样的：
         *
         * */
//        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                MLog.d(TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MLog.d(TAG, "onError: " + e);
//            }
//
//            @Override
//            public void onNext(Integer s) {
//                MLog.d(TAG, "onNext: " + s.toString());
//            }
//        };

        /**
         *
         * 3:
         *         action1  只执行了next方法
         *
         * */
        Action1<String> subscriber = new Action1<String>() {
            @Override
            public void call(String s) {
                MLog.d(TAG, "Action1_call: " + s.toString());
            }
        };

        /**
         * 二：
         *       创建 被观察者（主人）
         *       以下包含简化步骤
         *       Observable.OnSubscribe():当主人调用Observable.subscribe，并且called,就触发调用此方法
         *
         * */

        /**
         *   Observable.create
         *        //Observable.create(); //最基本的创造事件序列的方法
         *
         * */
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            /**
//             *    subscriber: 观察者对象
//             * */
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("0");
//                subscriber.onNext("1");
//                subscriber.onNext("2");
//
//                subscriber.onCompleted();
//            }
//        });


        /**
         *   Observable.just();   //将传入的参数依次发送出来。
         *   // 将会依次调用：
         // onNext("Hello");
         // onNext("Hi");
         // onNext("Aloha");
         // onCompleted();
         * */
        Observable observable = Observable.just("3", "1", "2"); //参数必须和观察者接受的一样

        /**
         *  Observable.from();   //将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
         *
         * */
//        String[] ayyays = {"1","2","3"};
//        Observable observable = Observable.from(ayyays);


        /**
         * Observable.range();  //从0开始   累加遍历10次     到9
         * 参数相当于执行了一个for循环
         *
         * */
        //Observable observable = Observable.range(0,10);


        //Observable.repeat(); //此方法无效


        /**
         * 三：
         *        主人 订阅 仆人
         *                 总结：    observer/subscriber 观察者     可为  Observer/  subscriber/  action1
         *                           observable       被观察者      可    create/    just/        from/    range/
         * */
        observable.subscribe(subscriber);
    }

    @OnClick({R.id.btn_1, R.id.image_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                observableSimple();

                break;
            case R.id.image_1:
                //根据uri获取图片并加载至控件
                loadImage();
                break;
        }
    }


    /***
     * subscribeOn(Schedulers.io())  //just，create中，比如通过网络获取uri数据。
     * 产生事件 例如从网络获取数据
     * 指定 subscribe() 所发生的线程   即 Observable.OnSubscribe 被激活时所处的线程。
     * <p/>
     * bserveOn(AndroidSchedulers.mainThread())   // 消费事件 例如展示数据至控件   指
     * 定 Subscriber 所运行在的线程。  比如onNext（）中显示draw至控件
     */
    private void loadImage() {
        Subscriber<Object> subscriber = new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                MLog.d(TAG, "onCompleted ");
                widget108.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                MLog.d(TAG, "onError： " + e);
            }

            @Override
            public void onNext(Object s) {
                MLog.d(TAG, "onNext： " + s);
                Glide.with(RxAndroidActivity.this).load(s).into(image1);
            }
        };

        Observable.create(new Observable.OnSubscribe<Object>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                widget108.setVisibility(View.VISIBLE);

                //延迟三秒
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String uri = "https://img3.doubanio.com//mpic//s11171603.jpg";
                        subscriber.onNext(uri);
                        subscriber.onCompleted();
                    }
                }, 3000);

            }
        })
//        .subscribeOn(Schedulers.immediate())          //call()  在当前线程
//        .observeOn(AndroidSchedulers.mainThread())    //为何在此必须在主线程中??
                .subscribe(subscriber)
        ;

    }


}
