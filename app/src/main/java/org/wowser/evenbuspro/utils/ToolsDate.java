package org.wowser.evenbuspro.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanli on 2016/5/26.
 */
public class ToolsDate {
    /**
     * 转换毫秒到具体时间, 小时:分钟:秒
     * 参考: http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
     *
     * @param millis 毫秒
     * @return 时间字符串
     */
    public static String convertMillis2Time(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.HOURS.toSeconds(TimeUnit.MICROSECONDS.toHours(millis)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
