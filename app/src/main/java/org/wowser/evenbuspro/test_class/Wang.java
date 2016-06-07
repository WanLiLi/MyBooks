package org.wowser.evenbuspro.test_class;

import org.wowser.evenbuspro.utils.MLog;

/**
 * 这个是小王
 *
 * @author xiaanming
 *         实现了一个回调接口CallBack，相当于----->背景一
 */
public class Wang implements CallBackInter {

    /**
     * 小李对象的引用
     * 相当于----->背景二
     */
    private Li li;


    public Wang(Li li) {
        this.li = li;
    }


    /**
     * 先执行thread后面的代码play
     *
     * */
    public void askQuestion(final String question) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 小王调用小李中的方法，在这里注册回调接口
                 * 这就相当于A类调用B的方法C
                 */
                li.executeMessage(Wang.this, question);
            }
        }).start();


        //小网问完问题挂掉电话就去干其他的事情了，诳街去了
        play();
    }


    public void play() {
        MLog.d("wanli_callback", "我小王要逛街去了");
    }


    @Override
    public void solve(String result) {
        MLog.d("wanli_callback", "result：" + result);
    }
}
